package com.example.Reservas.Service;
import com.example.Reservas.Repository.ReservasRepository;
import com.example.Reservas.exception.ResourceNotFoundException;
import com.example.Reservas.Model.Reserva;
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
public class ReservasService {

    private final ReservasRepository reservaRepository;
    public List<Reserva> findAll() {
        log.info("Obteniendo todas las reservas");
        return reservaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Reserva findById(Long id) {
        log.info("Buscando reserva con ID: {}", id);
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Reserva> findReservasActivas() {
        log.info("Obteniendo reservas activas");
        return reservaRepository.findReservasActivas();
    }

    @Transactional(readOnly = true)
    public List<Reserva> findReservasExpiradas() {
        log.info("Obteniendo reservas expiradas");
        return reservaRepository.findReservasExpiradas(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public List<Reserva> findByUsuarioId(Long usuarioId) {
        log.info("Buscando reservas del usuario ID: {}", usuarioId);
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public List<Reserva> findByLibroId(Long libroId) {
        log.info("Buscando reservas del libro ID: {}", libroId);
        return reservaRepository.findByLibroId(libroId);
    }

    @Transactional(readOnly = true)
    public List<Reserva> findReservasActivasByUsuario(Long usuarioId) {
        log.info("Buscando reservas activas del usuario ID: {}", usuarioId);
        return reservaRepository.findReservasActivasByUsuario(usuarioId);
    }

    @Transactional(readOnly = true)
    public List<Reserva> findReservasActivasByLibro(Long libroId) {
        log.info("Buscando reservas activas del libro ID: {}", libroId);
        return reservaRepository.findReservasActivasByLibro(libroId);
    }

    @Transactional(readOnly = true)
    public Long countReservasActivasByLibro(Long libroId) {
        log.info("Contando reservas activas del libro ID: {}", libroId);
        return reservaRepository.countReservasActivasByLibro(libroId);
    }

    @Transactional(readOnly = true)
    public Long countReservasActivasByUsuario(Long usuarioId) {
        log.info("Contando reservas activas del usuario ID: {}", usuarioId);
        return reservaRepository.countReservasActivasByUsuario(usuarioId);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        log.info("Verificando existencia de reserva con ID: {}", id);
        return reservaRepository.existsById(id);
    }

    // ========== MÉTODOS DE ESCRITURA ==========

    public Reserva crear(Reserva reserva) {
        log.info("Creando nueva reserva para libro ID: {}, usuario ID: {}",
                reserva.getLibroId(), reserva.getUsuarioId());

        // Validar que el libro existe (comunicación con servicio-libros)
        // validarLibroExiste(reserva.getLibroId());

        // Validar que el usuario existe (comunicación con servicio-usuarios)
        // validarUsuarioExiste(reserva.getUsuarioId());

        // Validar que el libro esté disponible
        // validarLibroDisponible(reserva.getLibroId());

        // Validar que el usuario no tenga reservas activas (máximo 3)
        Long reservasActivas = reservaRepository.countReservasActivasByUsuario(reserva.getUsuarioId());
        if (reservasActivas >= 3) {
            throw new IllegalArgumentException("El usuario ya tiene 3 reservas activas");
        }

        // Validar que el usuario no tenga una reserva activa para este libro
        reservaRepository.findReservaActivaByLibroAndUsuario(reserva.getLibroId(), reserva.getUsuarioId())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("El usuario ya tiene una reserva activa para este libro");
                });

        // Validar que no haya demasiadas reservas para el libro (máximo 5)
        Long reservasLibro = reservaRepository.countReservasActivasByLibro(reserva.getLibroId());
        if (reservasLibro >= 5) {
            throw new IllegalArgumentException("El libro ya tiene 5 reservas activas");
        }

        // Si no se especifica fecha de reserva, usar la fecha actual
        if (reserva.getFechaReserva() == null) {
            reserva.setFechaReserva(LocalDateTime.now());
        }

        // Calcular fecha de expiración (3 días después de la reserva)
        if (reserva.getFechaExpiracion() == null) {
            reserva.setFechaExpiracion(reserva.getFechaReserva().plusDays(3));
        }

        // Estado por defecto
        if (reserva.getEstado() == null) {
            reserva.setEstado("ACTIVA");
        }

        // Dias de validez por defecto
        if (reserva.getDiasValidez() == null) {
            reserva.setDiasValidez(3);
        }

        Reserva saved = reservaRepository.save(reserva);
        log.info("Reserva creada exitosamente con ID: {}", saved.getId());
        return saved;
    }

    public Reserva actualizar(Long id, Reserva reservaActualizada) {
        log.info("Actualizando reserva con ID: {}", id);

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con ID: " + id));

        // Solo se pueden actualizar ciertos campos
        reserva.setLibroId(reservaActualizada.getLibroId());
        reserva.setUsuarioId(reservaActualizada.getUsuarioId());
        reserva.setFechaReserva(reservaActualizada.getFechaReserva());
        reserva.setFechaExpiracion(reservaActualizada.getFechaExpiracion());
        reserva.setEstado(reservaActualizada.getEstado());
        reserva.setDiasValidez(reservaActualizada.getDiasValidez());
        reserva.setObservaciones(reservaActualizada.getObservaciones());

        Reserva updated = reservaRepository.save(reserva);
        log.info("Reserva actualizada exitosamente con ID: {}", updated.getId());
        return updated;
    }

    public void eliminar(Long id) {
        log.info("Eliminando reserva con ID: {}", id);
        if (!reservaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reserva no encontrada con ID: " + id);
        }
        reservaRepository.deleteById(id);
        log.info("Reserva eliminada exitosamente con ID: {}", id);
    }

    // ========== MÉTODOS DE VALIDACIÓN DE NEGOCIO ==========

    public void cancelarReserva(Long id) {
        log.info("Cancelando reserva con ID: {}", id);
        Reserva reserva = findById(id);

        if (!"ACTIVA".equals(reserva.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden cancelar reservas activas");
        }

        reserva.setEstado("CANCELADA");
        reservaRepository.save(reserva);
        log.info("Reserva cancelada exitosamente");
    }

    public void convertirAPrestamo(Long id) {
        log.info("Convirtiendo reserva ID: {} a préstamo", id);
        Reserva reserva = findById(id);

        if (!"ACTIVA".equals(reserva.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden convertir reservas activas");
        }

        if (reserva.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            reserva.setEstado("EXPIRADA");
            reservaRepository.save(reserva);
            throw new IllegalArgumentException("La reserva ya expiró");
        }

        // Verificar que el libro esté disponible
        // validarLibroDisponible(reserva.getLibroId());

        // Verificar que el usuario pueda hacer préstamos
        // validarUsuarioPuedePrestar(reserva.getUsuarioId());

        reserva.setEstado("CONVERTIDA");
        reservaRepository.save(reserva);

        // Aquí se llamaría al servicio de préstamos para crear el préstamo
        // crearPrestamo(reserva);

        log.info("Reserva convertida a préstamo exitosamente");
    }

    public void actualizarReservasExpiradas() {
        log.info("Actualizando reservas expiradas");
        List<Reserva> expiradas = reservaRepository.findReservasExpiradas(LocalDateTime.now());

        for (Reserva reserva : expiradas) {
            if ("ACTIVA".equals(reserva.getEstado())) {
                reserva.setEstado("EXPIRADA");
                reservaRepository.save(reserva);
                log.info("Reserva ID: {} marcada como expirada", reserva.getId());
            }
        }
    }

    public void extenderValidez(Long id, Integer diasExtra) {
        log.info("Extendiendo validez de reserva ID: {} por {} días", id, diasExtra);
        Reserva reserva = findById(id);

        if (!"ACTIVA".equals(reserva.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden extender reservas activas");
        }

        if (diasExtra <= 0 || diasExtra > 7) {
            throw new IllegalArgumentException("Los días extra deben ser entre 1 y 7");
        }

        reserva.setFechaExpiracion(reserva.getFechaExpiracion().plusDays(diasExtra));
        reserva.setDiasValidez(reserva.getDiasValidez() + diasExtra);
        reservaRepository.save(reserva);
        log.info("Validez extendida exitosamente. Nueva fecha de expiración: {}",
                reserva.getFechaExpiracion());
    }
}