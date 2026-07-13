package com.example.Autores.Controller;
import com.example.Autores.Model.Autor;
import com.example.Autores.Service.AutoresService;
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
@RequestMapping("/api/v1/autores")
@RequiredArgsConstructor
public class AutoresController {

    private final AutoresService autorService;

    @Operation(summary = "Obtener todos los autores")
    @GetMapping
    public ResponseEntity<List<Autor>> getAllAutores() {
        return ResponseEntity.ok(autorService.findAll());
    }

    @Operation(summary = "Obtener autor por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Autor> getAutorById(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.findById(id));
    }

    @Operation(summary = "Buscar autores por nombre")
    @GetMapping("/buscar")
    public ResponseEntity<List<Autor>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(autorService.buscarPorNombre(nombre));
    }

    @Operation(summary = "Buscar autores por nacionalidad")
    @GetMapping("/nacionalidad/{nacionalidad}")
    public ResponseEntity<List<Autor>> getByNacionalidad(@PathVariable String nacionalidad) {
        return ResponseEntity.ok(autorService.findByNacionalidad(nacionalidad));
    }

    @Operation(summary = "Crear un nuevo autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Autor> crearAutor(@Valid @RequestBody Autor autor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.crear(autor));
    }

    @Operation(summary = "Actualizar un autor existente")
    @PutMapping("/{id}")
    public ResponseEntity<Autor> actualizarAutor(
            @PathVariable Long id,
            @Valid @RequestBody Autor autor) {
        return ResponseEntity.ok(autorService.actualizar(id, autor));
    }

    @Operation(summary = "Eliminar un autor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAutor(@PathVariable Long id) {
        autorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
