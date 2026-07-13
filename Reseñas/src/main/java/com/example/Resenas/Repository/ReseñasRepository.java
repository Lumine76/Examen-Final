package com.example.Resenas.Repository;
import com.example.Resenas.Model.Reseña;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReseñasRepository extends JpaRepository<Reseña, Long> {

    @Query("SELECT r FROM Resena r WHERE r.libroId = :libroId ORDER BY r.fechaResena DESC")
    List<Reseña> findByLibroId(@Param("libroId") Long libroId);

    @Query("SELECT r FROM Resena r WHERE r.usuarioId = :usuarioId ORDER BY r.fechaResena DESC")
    List<Reseña> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("SELECT r FROM Resena r WHERE r.libroId = :libroId AND r.usuarioId = :usuarioId")
    Optional<Reseña> findByLibroIdAndUsuarioId(@Param("libroId") Long libroId, @Param("usuarioId") Long usuarioId);

    @Query("SELECT AVG(r.calificacion) FROM Resena r WHERE r.libroId = :libroId")
    Double calcularPromedioCalificacion(@Param("libroId") Long libroId);

    @Query("SELECT COUNT(r) FROM Resena r WHERE r.libroId = :libroId")
    Long countResenasByLibro(@Param("libroId") Long libroId);

    @Query("SELECT r FROM Resena r WHERE r.calificacion >= 4 ORDER BY r.fechaResena DESC")
    List<Reseña> findResenasRecomendadas();

    @Query("SELECT r FROM Resena r WHERE r.recomendado = true ORDER BY r.fechaResena DESC")
    List<Reseña> findResenasRecomendadasExplicitas();

    @Query("SELECT r FROM Resena r WHERE r.comentario IS NOT NULL AND LENGTH(r.comentario) > 0")
    List<Reseña> findResenasConComentario();
}