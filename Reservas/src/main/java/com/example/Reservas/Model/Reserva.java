package com.example.Reservas.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del libro es obligatorio")
    @Column(name = "libro_id", nullable = false)
    private Long libroId;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @NotNull(message = "La fecha de reserva es obligatoria")
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    @Column(name = "fecha_expiracion")
    private LocalDateTime fechaExpiracion;

    @NotBlank(message = "El estado es obligatorio")
    @Column(length = 20)
    private String estado = "ACTIVA"; // ACTIVA, EXPIRADA, CANCELADA, CONVERTIDA

    @Column(name = "dias_validez")
    private Integer diasValidez = 3; // Días que dura la reserva

    @Column(length = 255)
    private String observaciones;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
