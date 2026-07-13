package com.example.libreria.Controller;
import com.example.libreria.dto.RequestDTO;
import com.example.libreria.dto.dto;
import com.example.libreria.Service.LibroService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/libros")
@RequiredArgsConstructor
public class LibroController {

    private final LibroService libroService;

    @GetMapping
    public ResponseEntity<List<dto>> getAllLibros() {
        return ResponseEntity.ok(libroService.findAll());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<dto> getLibroById(
            @Parameter(description = "ID del libro") @PathVariable Long id) {
        return ResponseEntity.ok(libroService.findById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<dto>> buscarPorTitulo(
            @RequestParam String titulo) {
        return ResponseEntity.ok(libroService.buscarPorTitulo(titulo));
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<dto>> getByGenero(@PathVariable String genero) {
        return ResponseEntity.ok(libroService.findByGenero(genero));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<dto>> getDisponibles() {
        return ResponseEntity.ok(libroService.findDisponibles());
    }

    @GetMapping("/autor/{autorId}")
    public ResponseEntity<List<dto>> getByAutor(@PathVariable Long autorId) {
        return ResponseEntity.ok(libroService.findByAutor(autorId));
    }

    @GetMapping("/anio/{anio}")
    public ResponseEntity<List<dto>> getByAnio(@PathVariable Integer anio) {
        return ResponseEntity.ok(libroService.findByAnioPublicacion(anio));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Libro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<dto> crearLibro(@Valid @RequestBody RequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<dto> actualizarLibro(
            @PathVariable Long id,
            @Valid @RequestBody RequestDTO request) {
        return ResponseEntity.ok(libroService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/incrementar")
    public ResponseEntity<Void> incrementarCantidad(
            @PathVariable Long id,
            @RequestParam Integer cantidad) {
        libroService.incrementarCantidad(id, cantidad);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/decrementar")
    public ResponseEntity<Void> decrementarCantidad(
            @PathVariable Long id,
            @RequestParam Integer cantidad) {
        libroService.decrementarCantidad(id, cantidad);
        return ResponseEntity.ok().build();
    }
}
