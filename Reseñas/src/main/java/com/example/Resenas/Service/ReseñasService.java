package com.example.Resenas.Service;
import com.example.Resenas.Repository.ReseñasRepository;
import com.example.Resenas.exception.ResourceNotFoundException;
import com.example.Resenas.Model.Reseña;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReseñasService {

    private final ReseñasRepository resenaRepository;
    @Transactional(readOnly = true)
    public List<Reseña> findAll() {
        log.info("Obteniendo todas las reseñas");
        return resenaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Reseña findById(Long id) {
        log.info("Buscando reseña con ID: {}", id);
        return resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reseña no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Reseña> findByLibroId(Long libroId) {
        log.info("Buscando reseñas del libro ID: {}", libroId);
        return resenaRepository.findByLibroId(libroId);
    }

    @Transactional(readOnly = true)
    public List<Reseña> findByUsuarioId(Long usuarioId) {
        log.info("Buscando reseñas del usuario ID: {}", usuarioId);
        return resenaRepository.findByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public Reseña findByLibroIdAndUsuarioId(Long libroId, Long usuarioId) {
        log.info("Buscando reseña del libro ID: {} y usuario ID: {}", libroId, usuarioId);
        return resenaRepository.findByLibroIdAndUsuarioId(libroId, usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No se encontró reseña para el libro ID: " + libroId + " y usuario ID: " + usuarioId));
    }

    @Transactional(readOnly = true)
    public Double calcularPromedioCalificacion(Long libroId) {
        log.info("Calculando promedio de calificaciones del libro ID: {}", libroId);
        Double promedio = resenaRepository.calcularPromedioCalificacion(libroId);
        return promedio != null ? promedio : 0.0;
    }

    @Transactional(readOnly = true)
    public Long countResenasByLibro(Long libroId) {
        log.info("Contando reseñas del libro ID: {}", libroId);
        return resenaRepository.countResenasByLibro(libroId);
    }

    @Transactional(readOnly = true)
    public List<Reseña> findResenasRecomendadas() {
        log.info("Obteniendo reseñas recomendadas (calificación >= 4)");
        return resenaRepository.findResenasRecomendadas();
    }

    @Transactional(readOnly = true)
    public List<Reseña> findResenasRecomendadasExplicitas() {
        log.info("Obteniendo reseñas marcadas como recomendadas");
        return resenaRepository.findResenasRecomendadasExplicitas();
    }

    @Transactional(readOnly = true)
    public List<Reseña> findResenasConComentario() {
        log.info("Obteniendo reseñas con comentario");
        return resenaRepository.findResenasConComentario();
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        log.info("Verificando existencia de reseña con ID: {}", id);
        return resenaRepository.existsById(id);
    }

    public Reseña crear(Reseña resena) {
        log.info("Creando nueva reseña para libro ID: {}, usuario ID: {}",
                resena.getLibroId(), resena.getUsuarioId());
        resenaRepository.findByLibroIdAndUsuarioId(resena.getLibroId(), resena.getUsuarioId())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("El usuario ya ha reseñado este libro");
                });
        if (resena.getFechaResena() == null) {
            resena.setFechaResena(LocalDateTime.now());
        }
        if (resena.getRecomendado() == null) {
            resena.setRecomendado(resena.getCalificacion() >= 4);
        }

        Reseña saved = resenaRepository.save(resena);
        log.info("Reseña creada exitosamente con ID: {}", saved.getId());
        return saved;
    }

    public Reseña actualizar(Long id, Reseña resenaActualizada) {
        log.info("Actualizando reseña con ID: {}", id);

        Reseña resena = resenaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reseña no encontrada con ID: " + id));

        resena.setCalificacion(resenaActualizada.getCalificacion());
        resena.setComentario(resenaActualizada.getComentario());
        resena.setRecomendado(resenaActualizada.getCalificacion() >= 4);
        resena.setFechaResena(LocalDateTime.now());

        Reseña updated = resenaRepository.save(resena);
        log.info("Reseña actualizada exitosamente con ID: {}", updated.getId());
        return updated;
    }
    public void eliminar(Long id) {
        log.info("Eliminando reseña con ID: {}", id);
        if (!resenaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reseña no encontrada con ID: " + id);
        }
        resenaRepository.deleteById(id);
        log.info("Reseña eliminada exitosamente con ID: {}", id);
    }
    public void marcarComoRecomendada(Long id) {
        log.info("Marcando reseña ID: {} como recomendada", id);
        Reseña resena = findById(id);
        resena.setRecomendado(true);
        resenaRepository.save(resena);
        log.info("Reseña marcada como recomendada exitosamente");
    }

    public void desmarcarComoRecomendada(Long id) {
        log.info("Desmarcando reseña ID: {} como recomendada", id);
        Reseña resena = findById(id);
        resena.setRecomendado(false);
        resenaRepository.save(resena);
        log.info("Reseña desmarcada como recomendada exitosamente");
    }
}
