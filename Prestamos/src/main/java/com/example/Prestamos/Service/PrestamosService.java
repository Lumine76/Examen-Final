package com.example.Prestamos.Service;
import com.example.Prestamos.Repository.PrestamosRepository;
import com.example.Prestamos.Repository.PrestamosRepository;
import com.example.Prestamos.exception.ResourceNotFoundException;
import com.example.Prestamos.Model.PrestamosModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PrestamosService {

    private final PrestamosRepository prestamoRepository;

    @Transactional(readOnly = true)
    public List<PrestamosModel> findAll() {
        log.info("Obteniendo todos los préstamos");
        return prestamoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PrestamosModel findById(Long id) {
        log.info("Buscando préstamo con ID: {}", id);
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado con ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<PrestamosModel> findPrestamosActivos() {
        log.info("Obteniendo préstamos activos");
        return prestamoRepository.findPrestamosActivos();
    }

    @Transactional(readOnly = true)
    public List<PrestamosModel> findPrestamosAtrasados() {
        log.info("Obteniendo préstamos atrasados");
        return prestamoRepository.findPrestamosAtrasados(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public List<PrestamosModel> findByLibroId(Long libroId) {
        log.info("Buscando préstamos del libro ID: {}", libroId);
        return prestamoRepository.findByLibroId(libroId);
    }

    @Transactional(readOnly = true)
    public List<PrestamosModel> findByUsuarioId(Long usuarioId) {
        log.info("Buscando préstamos del usuario ID: {}", usuarioId);
        return prestamoRepository.findByUsuarioId(usuarioId);
    }

    @Transactional(readOnly = true)
    public List<PrestamosModel> findPrestamosActivosByUsuario(Long usuarioId) {
        log.info("Buscando préstamos activos del usuario ID: {}", usuarioId);
        return prestamoRepository.findPrestamosActivosByUsuario(usuarioId);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        log.info("Verificando existencia de préstamo con ID: {}", id);
        return prestamoRepository.existsById(id);
    }

    // ========== MÉTODOS DE ESCRITURA ==========

    public PrestamosModel crear(PrestamosModel prestamo) {
        log.info("Creando nuevo préstamo para libro ID: {}, usuario ID: {}",
                prestamo.getLibroId(), prestamo.getUsuarioId());

        // Validar que el libro existe (comunicación con servicio-libros)
        // validarLibroExiste(prestamo.getLibroId());

        // Validar que el usuario existe (comunicación con servicio-usuarios)
        // validarUsuarioExiste(prestamo.getUsuarioId());

        // Validar que el usuario no tenga préstamos activos (máximo 3)
        List<PrestamosModel> prestamosActivos = prestamoRepository.findPrestamosActivosByUsuario(
                prestamo.getUsuarioId());
        if (prestamosActivos.size() >= 3) {
            throw new IllegalArgumentException("El usuario ya tiene 3 préstamos activos");
        }

        // Validar que el libro esté disponible
        // validarLibroDisponible(prestamo.getLibroId());

        // Si no se especifica fecha de préstamo, usar la fecha actual
        if (prestamo.getFechaPrestamo() == null) {
            prestamo.setFechaPrestamo(LocalDate.now());
        }

        // Calcular fecha de devolución (7 días después del préstamo)
        if (prestamo.getFechaDevolucion() == null) {
            prestamo.setFechaDevolucion(prestamo.getFechaPrestamo().plusDays(7));
        }

        // Estado por defecto
        if (prestamo.getEstado() == null) {
            prestamo.setEstado("ACTIVO");
        }

        PrestamosModel saved = prestamoRepository.save(prestamo);
        log.info("Préstamo creado exitosamente con ID: {}", saved.getId());
        return saved;
    }

    public PrestamosModel actualizar(Long id, PrestamosModel prestamoActualizado) {
        log.info("Actualizando préstamo con ID: {}", id);

        PrestamosModel prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado con ID: " + id));

        prestamo.setLibroId(prestamoActualizado.getLibroId());
        prestamo.setUsuarioId(prestamoActualizado.getUsuarioId());
        prestamo.setFechaPrestamo(prestamoActualizado.getFechaPrestamo());
        prestamo.setFechaDevolucion(prestamoActualizado.getFechaDevolucion());
        prestamo.setEstado(prestamoActualizado.getEstado());
        prestamo.setDiasPrestamo(prestamoActualizado.getDiasPrestamo());

        PrestamosModel updated = prestamoRepository.save(prestamo);
        log.info("Préstamo actualizado exitosamente con ID: {}", updated.getId());
        return updated;
    }

    public void eliminar(Long id) {
        log.info("Eliminando préstamo con ID: {}", id);
        if (!prestamoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Préstamo no encontrado con ID: " + id);
        }
        prestamoRepository.deleteById(id);
        log.info("Préstamo eliminado exitosamente con ID: {}", id);
    }

    // ========== MÉTODOS DE VALIDACIÓN DE NEGOCIO ==========

    public void devolverPrestamo(Long id) {
        log.info("Devolviendo préstamo con ID: {}", id);
        PrestamosModel prestamo = findById(id);

        if ("DEVUELTO".equals(prestamo.getEstado())) {
            throw new IllegalArgumentException("El préstamo ya fue devuelto");
        }

        prestamo.setEstado("DEVUELTO");
        prestamo.setFechaDevolucion(LocalDate.now());
        prestamoRepository.save(prestamo);

        // Incrementar stock del libro
        // incrementarStockLibro(prestamo.getLibroId());

        log.info("Préstamo devuelto exitosamente");
    }

    public void actualizarEstadoAtrasados() {
        log.info("Actualizando préstamos atrasados");
        List<PrestamosModel> atrasados = prestamoRepository.findPrestamosAtrasados(LocalDate.now());

        for (PrestamosModel prestamo : atrasados) {
            if ("ACTIVO".equals(prestamo.getEstado())) {
                prestamo.setEstado("ATRASADO");
                prestamoRepository.save(prestamo);
                log.info("Préstamo ID: {} marcado como atrasado", prestamo.getId());
            }
        }
    }

    public void renovarPrestamo(Long id, Integer diasExtra) {
        log.info("Renovando préstamo ID: {} por {} días", id, diasExtra);
        PrestamosModel prestamo = findById(id);

        if (!"ACTIVO".equals(prestamo.getEstado())) {
            throw new IllegalArgumentException("Solo se pueden renovar préstamos activos");
        }

        if (diasExtra <= 0 || diasExtra > 7) {
            throw new IllegalArgumentException("Los días extra deben ser entre 1 y 7");
        }

        prestamo.setFechaDevolucion(prestamo.getFechaDevolucion().plusDays(diasExtra));
        prestamoRepository.save(prestamo);
        log.info("Préstamo renovado exitosamente. Nueva fecha de devolución: {}",
                prestamo.getFechaDevolucion());
    }
}
