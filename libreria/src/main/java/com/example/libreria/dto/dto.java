package com.example.libreria.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class dto {
    private Long id;
    private String titulo;
    private Integer anioPublicacion;
    private String genero;
    private Integer cantidadDisponible;
    private Long autorId;
    private String nombreAutor;
    private String apellidoAutor;
}
