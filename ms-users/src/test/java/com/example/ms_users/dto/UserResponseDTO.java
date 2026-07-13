package com.example.ms_users.dto;

import com.example.ms_users.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private Role role;
    private Boolean activo;
    private Boolean emailVerificado;
    private LocalDateTime fechaRegistro;
    private LocalDateTime ultimoAcceso;
}

