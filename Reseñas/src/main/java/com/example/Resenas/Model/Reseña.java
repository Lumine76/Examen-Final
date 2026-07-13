package com.example.Resenas.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "resenas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"libro_id", "usuario_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reseña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del libro es obligatorio")
    @Column(name = "libro_id", nullable = false)
    private Long libroId;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Min(value = 1, message = "La calificación mínima es 1")
    @Max(value = 5, message = "La calificación máxima es 5")
    @Column(nullable = false)
    private Integer calificacion;

    @Size(max = 1000, message = "El comentario no puede exceder 1000 caracteres")
    @Column(length = 1000)
    private String comentario;

    @Column(name = "recomendado")
    private Boolean recomendado;

    @Column(name = "fecha_resena", nullable = false)
    private LocalDateTime fechaResena;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
