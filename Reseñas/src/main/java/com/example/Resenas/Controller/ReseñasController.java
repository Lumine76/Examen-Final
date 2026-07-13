package com.example.Resenas.Controller;
import com.example.Resenas.Model.Reseña;
import com.example.Resenas.Service.ReseñasService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/resenas")
@RequiredArgsConstructor
public class ReseñasController {

    private final ReseñasService resenaService;

    @Operation(summary = "Obtener todas las reseñas")
    @GetMapping
    public ResponseEntity<List<Reseña>> getAllResenas() {
        return ResponseEntity.ok(resenaService.findAll());
    }

    @Operation(summary = "Obtener reseña por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
            @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reseña> getResenaById(
            @Parameter(description = "ID de la reseña") @PathVariable Long id) {
        return ResponseEntity.ok(resenaService.findById(id));
    }

    @Operation(summary = "Obtener reseñas por libro")
    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<Reseña>> getByLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(resenaService.findByLibroId(libroId));
    }

    @Operation(summary = "Obtener reseñas por usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reseña>> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(resenaService.findByUsuarioId(usuarioId));
    }

    @Operation(summary = "Obtener reseña por libro y usuario")
    @GetMapping("/libro/{libroId}/usuario/{usuarioId}")
    public ResponseEntity<Reseña> getByLibroAndUsuario(
            @PathVariable Long libroId,
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(resenaService.findByLibroIdAndUsuarioId(libroId, usuarioId));
    }

    @Operation(summary = "Calcular promedio de calificaciones de un libro")
    @GetMapping("/libro/{libroId}/promedio")
    public ResponseEntity<Double> getPromedioCalificacion(@PathVariable Long libroId) {
        return ResponseEntity.ok(resenaService.calcularPromedioCalificacion(libroId));
    }

    @Operation(summary = "Contar reseñas de un libro")
    @GetMapping("/libro/{libroId}/count")
    public ResponseEntity<Long> countResenasByLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(resenaService.countResenasByLibro(libroId));
    }

    @Operation(summary = "Obtener reseñas recomendadas (calificación >= 4)")
    @GetMapping("/recomendadas")
    public ResponseEntity<List<Reseña>> getResenasRecomendadas() {
        return ResponseEntity.ok(resenaService.findResenasRecomendadas());
    }

    @Operation(summary = "Obtener reseñas marcadas como recomendadas")
    @GetMapping("/recomendadas/explicitas")
    public ResponseEntity<List<Reseña>> getResenasRecomendadasExplicitas() {
        return ResponseEntity.ok(resenaService.findResenasRecomendadasExplicitas());
    }

    @Operation(summary = "Obtener reseñas con comentario")
    @GetMapping("/con-comentario")
    public ResponseEntity<List<Reseña>> getResenasConComentario() {
        return ResponseEntity.ok(resenaService.findResenasConComentario());
    }

    @Operation(summary = "Crear una nueva reseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reseña creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Reseña> crearResena(@Valid @RequestBody Reseña resena) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaService.crear(resena));
    }

    @Operation(summary = "Actualizar una reseña existente")
    @PutMapping("/{id}")
    public ResponseEntity<Reseña> actualizarResena(
            @PathVariable Long id,
            @Valid @RequestBody Reseña resena) {
        return ResponseEntity.ok(resenaService.actualizar(id, resena));
    }

    @Operation(summary = "Eliminar una reseña")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        resenaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Marcar reseña como recomendada")
    @PatchMapping("/{id}/recomendar")
    public ResponseEntity<Void> marcarComoRecomendada(@PathVariable Long id) {
        resenaService.marcarComoRecomendada(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Desmarcar reseña como recomendada")
    @PatchMapping("/{id}/no-recomendar")
    public ResponseEntity<Void> desmarcarComoRecomendada(@PathVariable Long id) {
        resenaService.desmarcarComoRecomendada(id);
        return ResponseEntity.ok().build();
    }
}
