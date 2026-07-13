package com.example.Usuarios.Controller;
import com.example.Usuarios.Model.Usuario;
import com.example.Usuarios.Service.UsuariosService;
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
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuariosController {

    private final UsuariosService usuarioService;

    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @Operation(summary = "Obtener usuario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @Operation(summary = "Obtener usuario por email")
    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> getUsuarioByEmail(@PathVariable String email) {
        return ResponseEntity.ok(usuarioService.findByEmail(email));
    }

    @Operation(summary = "Buscar usuarios por nombre")
    @GetMapping("/buscar")
    public ResponseEntity<List<Usuario>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(usuarioService.buscarPorNombre(nombre));
    }

    @Operation(summary = "Obtener usuarios activos")
    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> getUsuariosActivos() {
        return ResponseEntity.ok(usuarioService.findActivos());
    }

    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crear(usuario));
    }

    @Operation(summary = "Actualizar un usuario existente")
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable Long id,
            @Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.actualizar(id, usuario));
    }

    @Operation(summary = "Eliminar un usuario")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Activar un usuario")
    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activarUsuario(@PathVariable Long id) {
        usuarioService.activarUsuario(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Desactivar un usuario")
    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarUsuario(@PathVariable Long id) {
        usuarioService.desactivarUsuario(id);
        return ResponseEntity.ok().build();
    }
}
