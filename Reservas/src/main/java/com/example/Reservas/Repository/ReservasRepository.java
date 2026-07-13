package com.example.Reservas.Repository;
import com.example.Reservas.Model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservasRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT r FROM Reserva r WHERE r.estado = 'ACTIVA'")
    List<Reserva> findReservasActivas();

    @Query("SELECT r FROM Reserva r WHERE r.estado = 'ACTIVA' AND r.fechaExpiracion < :fecha")
    List<Reserva> findReservasExpiradas(LocalDateTime fecha);

    @Query("SELECT r FROM Reserva r WHERE r.libroId = :libroId AND r.estado = 'ACTIVA'")
    List<Reserva> findReservasActivasByLibro(@Param("libroId") Long libroId);

    @Query("SELECT r FROM Reserva r WHERE r.usuarioId = :usuarioId AND r.estado = 'ACTIVA'")
    List<Reserva> findReservasActivasByUsuario(@Param("usuarioId") Long usuarioId);

    @Query("SELECT r FROM Reserva r WHERE r.usuarioId = :usuarioId")
    List<Reserva> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT r FROM Reserva r WHERE r.libroId = :libroId")
    List<Reserva> findByLibroId(@Param("libroId") Long libroId);

    @Query("SELECT r FROM Reserva r WHERE r.libroId = :libroId AND r.usuarioId = :usuarioId AND r.estado = 'ACTIVA'")
    Optional<Reserva> findReservaActivaByLibroAndUsuario(@Param("libroId") Long libroId, @Param("usuarioId") Long usuarioId);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.libroId = :libroId AND r.estado = 'ACTIVA'")
    Long countReservasActivasByLibro(@Param("libroId") Long libroId);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.usuarioId = :usuarioId AND r.estado = 'ACTIVA'")
    Long countReservasActivasByUsuario(@Param("usuarioId") Long usuarioId);
}
