package com.example.libreria.Service;
import com.example.libreria.Model.Libro;
import com.example.libreria.Repository.LibroRepository;
import com.example.libreria.dto.RequestDTO;
import com.example.libreria.dto.dto;
import com.example.libreria.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LibroService {

    private final LibroRepository libroRepository;


    public List<dto> findAll() {
        log.info("Obteniendo todos los libros");
        return libroRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public dto findById(Long id) {
        log.info("Buscando libro con ID: {}", id);
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ID: " + id));
        return convertToDTO(libro);
    }
    public List<dto> findByAutor(Long autorId) {
        log.info("Buscando libros del autor con ID: {}", autorId);
        return libroRepository.findByAutorId(autorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<dto> findDisponibles() {
        log.info("Obteniendo libros disponibles");
        return libroRepository.findLibrosDisponibles().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<dto> buscarPorTitulo(String titulo) {
        log.info("Buscando libros por título: {}", titulo);
        return libroRepository.buscarPorTitulo(titulo).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<dto> findByGenero(String genero) {
        log.info("Buscando libros por género: {}", genero);
        return libroRepository.findByGenero(genero).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<dto> findByAnioPublicacion(Integer anio) {
        log.info("Buscando libros por año de publicación: {}", anio);
        return libroRepository.findByAnioPublicacion(anio).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        log.info("Verificando existencia de libro con ID: {}", id);
        return libroRepository.existsById(id);
    }
    public dto crear(RequestDTO request) {
        log.info("Creando nuevo libro: {}", request.getTitulo());
        if (request.getAnioPublicacion() != null && request.getAnioPublicacion() > 2026) {
            throw new IllegalArgumentException("El año de publicación no puede ser futuro");
        }
        Libro libro = convertToEntity(request);
        Libro saved = libroRepository.save(libro);
        log.info("Libro creado exitosamente con ID: {}", saved.getId());
        return convertToDTO(saved);
    }

    public dto actualizar(Long id, RequestDTO request) {
        log.info("Actualizando libro con ID: {}", id);

        Libro libro = libroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ID: " + id));
        if (request.getAnioPublicacion() != null && request.getAnioPublicacion() > 2026) {
            throw new IllegalArgumentException("El año de publicación no puede ser futuro");
        }
        libro.setTitulo(request.getTitulo());
        libro.setAnioPublicacion(request.getAnioPublicacion());
        libro.setGenero(request.getGenero());
        libro.setCantidadDisponible(request.getCantidadDisponible());
        libro.setAutorId(request.getAutorId());

        Libro updated = libroRepository.save(libro);
        log.info("Libro actualizado exitosamente con ID: {}", updated.getId());

        return convertToDTO(updated);
    }

    public void eliminar(Long id) {
        log.info("Eliminando libro con ID: {}", id);
        if (!libroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Libro no encontrado con ID: " + id);
        }
        libroRepository.deleteById(id);
        log.info("Libro eliminado exitosamente con ID: {}", id);
    }
    public void incrementarCantidad(Long id, Integer cantidad) {
        log.info("Incrementando cantidad del libro ID: {} en {}", id, cantidad);
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ID: " + id));
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a incrementar debe ser positiva");
        }
        libro.setCantidadDisponible(libro.getCantidadDisponible() + cantidad);
        libroRepository.save(libro);
        log.info("Cantidad incrementada exitosamente. Nueva cantidad: {}", libro.getCantidadDisponible());
    }
    public void decrementarCantidad(Long id, Integer cantidad) {
        log.info("Decrementando cantidad del libro ID: {} en {}", id, cantidad);
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ID: " + id));
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a decrementar debe ser positiva");
        }

        if (libro.getCantidadDisponible() < cantidad) {
            throw new IllegalArgumentException("No hay suficientes ejemplares disponibles. Actual: "
                    + libro.getCantidadDisponible() + ", Solicitado: " + cantidad);
        }

        libro.setCantidadDisponible(libro.getCantidadDisponible() - cantidad);
        libroRepository.save(libro);
        log.info("Cantidad decrementada exitosamente. Nueva cantidad: {}", libro.getCantidadDisponible());
    }
    private dto convertToDTO(Libro libro) {
        dto dto = new dto();
        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setAnioPublicacion(libro.getAnioPublicacion());
        dto.setGenero(libro.getGenero());
        dto.setCantidadDisponible(libro.getCantidadDisponible());
        dto.setAutorId(libro.getAutorId());
        return dto;
    }
    private Libro convertToEntity(RequestDTO request) {
        Libro libro = new Libro();
        libro.setTitulo(request.getTitulo());
        libro.setAnioPublicacion(request.getAnioPublicacion());
        libro.setGenero(request.getGenero());
        libro.setCantidadDisponible(request.getCantidadDisponible());
        libro.setAutorId(request.getAutorId());
        return libro;
    }
}