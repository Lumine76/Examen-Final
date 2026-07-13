package com.example.Prestamos.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "prestamos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del libro es obligatorio")
    @Column(name = "libro_id", nullable = false)
    private Long libroId;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @NotNull(message = "La fecha de préstamo es obligatoria")
    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @NotNull(message = "La fecha de devolución es obligatoria")
    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    @NotBlank(message = "El estado es obligatorio")
    @Column(length = 20)
    private String estado = "ACTIVO"; // ACTIVO, DEVUELTO, ATRASADO

    @Column(name = "dias_prestamo")
    private Integer diasPrestamo = 7; // Días permitidos para el préstamo

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
