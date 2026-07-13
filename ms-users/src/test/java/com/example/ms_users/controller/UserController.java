package com.example.ms_users.controller;
import com.example.ms_users.dto.UserRequestDTO;
import com.example.ms_users.dto.UserResponseDTO;
import com.example.ms_users.enums.Role;
import com.example.ms_users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(summary = "Obtener usuario por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Obtener usuario por email")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @Operation(summary = "Obtener usuarios activos")
    @GetMapping("/activos")
    public ResponseEntity<List<UserResponseDTO>> getActiveUsers() {
        return ResponseEntity.ok(userService.findActiveUsers());
    }

    @Operation(summary = "Obtener usuarios por rol")
    @GetMapping("/rol/{role}")
    public ResponseEntity<List<UserResponseDTO>> getUsersByRole(@PathVariable Role role) {
        return ResponseEntity.ok(userService.findByRole(role));
    }

    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(request));
    }

    @Operation(summary = "Actualizar un usuario existente")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @Operation(summary = "Eliminar un usuario")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Activar un usuario")
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activateUser(@PathVariable Long id) {
        userService.activateUser(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Desactivar un usuario")
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Verificar email de un usuario")
    @PatchMapping("/{id}/verificar-email")
    public ResponseEntity<Void> verifyEmail(@PathVariable Long id) {
        userService.verifyEmail(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cambiar contraseña de un usuario")
    @PatchMapping("/{id}/cambiar-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable Long id,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        userService.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok().build();
    }
}