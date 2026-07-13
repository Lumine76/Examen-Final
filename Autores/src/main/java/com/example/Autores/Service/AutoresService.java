package com.example.Autores.Service;
import com.example.Autores.Model.Autor;
import com.example.Autores.Repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AutoresService {

    private final AutorRepository autorRepository;
    public List<Autor> findAll() {
        log.info("Obteniendo todos los autores");
        return autorRepository.findAllOrderedByApellido();
    }

    public Autor findById(Long id) {
        log.info("Buscando autor con ID: {}", id);
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con ID: " + id));
    }

    public List<Autor> buscarPorNombre(String nombre) {
        log.info("Buscando autores por nombre: {}", nombre);
        return autorRepository.buscarPorNombre(nombre);
    }

    public List<Autor> findByNacionalidad(String nacionalidad) {
        log.info("Buscando autores por nacionalidad: {}", nacionalidad);
        return autorRepository.findByNacionalidad(nacionalidad);
    }

    public boolean existsById(Long id) {
        log.info("Verificando existencia de autor con ID: {}", id);
        return autorRepository.existsById(id);
    }

    public Autor crear(Autor autor) {
        log.info("Creando nuevo autor: {} {}", autor.getNombre(), autor.getApellido());
        autorRepository.findByNombreAndApellido(autor.getNombre(), autor.getApellido())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Ya existe un autor con el nombre: "
                            + autor.getNombre() + " " + autor.getApellido());
                });
        if (autor.getFechaNacimiento() != null) {
            LocalDate hoy = LocalDate.now();
            LocalDate hace18Anios = hoy.minusYears(18);
            if (autor.getFechaNacimiento().isAfter(hace18Anios)) {
                throw new IllegalArgumentException("El autor debe tener al menos 18 años");
            }
        }
        Autor saved = autorRepository.save(autor);
        log.info("Autor creado exitosamente con ID: {}", saved.getId());
        return saved;
    }

    public Autor actualizar(Long id, Autor autorActualizado) {
        log.info("Actualizando autor con ID: {}", id);
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor no encontrado con ID: " + id));
        autorRepository.findByNombreAndApellido(
                autorActualizado.getNombre(),
                autorActualizado.getApellido()
        ).ifPresent(existing -> {
            if (!existing.getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe otro autor con el nombre: "
                        + autorActualizado.getNombre() + " " + autorActualizado.getApellido());
            }
        });

        autor.setNombre(autorActualizado.getNombre());
        autor.setApellido(autorActualizado.getApellido());
        autor.setFechaNacimiento(autorActualizado.getFechaNacimiento());
        autor.setBiografia(autorActualizado.getBiografia());
        autor.setNacionalidad(autorActualizado.getNacionalidad());

        Autor updated = autorRepository.save(autor);
        log.info("Autor actualizado exitosamente con ID: {}", updated.getId());
        return updated;
    }

    public void eliminar(Long id) {
        log.info("Eliminando autor con ID: {}", id);
        if (!autorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Autor no encontrado con ID: " + id);
        }
        autorRepository.deleteById(id);
        log.info("Autor eliminado exitosamente con ID: {}", id);
    }

    public Autor obtenerNombreCompleto(Long id) {
        log.info("Obteniendo nombre completo del autor con ID: {}", id);
        Autor autor = findById(id);
        return autor;
    }
}