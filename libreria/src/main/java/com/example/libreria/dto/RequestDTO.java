package com.example.libreria.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

    @NotBlank
    private String titulo;
    private Integer anioPublicacion;
    @NotBlank
    private String genero;
    private Integer cantidadDisponible = 0;
    @NotNull
    private Long autorId;
}
