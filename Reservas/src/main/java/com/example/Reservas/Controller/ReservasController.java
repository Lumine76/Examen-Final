package com.example.Reservas.Controller;
import com.example.Reservas.Model.Reserva;
import com.example.Reservas.Service.ReservasService;
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
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
public class ReservasController {

    private final ReservasService reservaService;

    @Operation(summary = "Obtener todas las reservas")
    @GetMapping
    public ResponseEntity<List<Reserva>> getAllReservas() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    @Operation(summary = "Obtener reserva por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva encontrada"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.findById(id));
    }

    @Operation(summary = "Obtener reservas activas")
    @GetMapping("/activas")
    public ResponseEntity<List<Reserva>> getReservasActivas() {
        return ResponseEntity.ok(reservaService.findReservasActivas());
    }

    @Operation(summary = "Obtener reservas expiradas")
    @GetMapping("/expiradas")
    public ResponseEntity<List<Reserva>> getReservasExpiradas() {
        return ResponseEntity.ok(reservaService.findReservasExpiradas());
    }

    @Operation(summary = "Obtener reservas por usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reserva>> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.findByUsuarioId(usuarioId));
    }

    @Operation(summary = "Obtener reservas por libro")
    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<Reserva>> getByLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(reservaService.findByLibroId(libroId));
    }

    @Operation(summary = "Obtener reservas activas por usuario")
    @GetMapping("/usuario/{usuarioId}/activas")
    public ResponseEntity<List<Reserva>> getReservasActivasByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.findReservasActivasByUsuario(usuarioId));
    }

    @Operation(summary = "Obtener reservas activas por libro")
    @GetMapping("/libro/{libroId}/activas")
    public ResponseEntity<List<Reserva>> getReservasActivasByLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(reservaService.findReservasActivasByLibro(libroId));
    }

    @Operation(summary = "Contar reservas activas por libro")
    @GetMapping("/libro/{libroId}/count")
    public ResponseEntity<Long> countReservasActivasByLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(reservaService.countReservasActivasByLibro(libroId));
    }

    @Operation(summary = "Contar reservas activas por usuario")
    @GetMapping("/usuario/{usuarioId}/count")
    public ResponseEntity<Long> countReservasActivasByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.countReservasActivasByUsuario(usuarioId));
    }

    @Operation(summary = "Crear una nueva reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@Valid @RequestBody Reserva reserva) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crear(reserva));
    }

    @Operation(summary = "Actualizar una reserva existente")
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(
            @PathVariable Long id,
            @Valid @RequestBody Reserva reserva) {
        return ResponseEntity.ok(reservaService.actualizar(id, reserva));
    }

    @Operation(summary = "Eliminar una reserva")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancelar una reserva")
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelarReserva(@PathVariable Long id) {
        reservaService.cancelarReserva(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Convertir reserva a préstamo")
    @PatchMapping("/{id}/convertir")
    public ResponseEntity<Void> convertirAPrestamo(@PathVariable Long id) {
        reservaService.convertirAPrestamo(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Extender validez de una reserva")
    @PatchMapping("/{id}/extender")
    public ResponseEntity<Void> extenderValidez(
            @PathVariable Long id,
            @RequestParam Integer diasExtra) {
        reservaService.extenderValidez(id, diasExtra);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Actualizar reservas expiradas (tarea programada)")
    @PatchMapping("/actualizar-expiradas")
    public ResponseEntity<Void> actualizarReservasExpiradas() {
        reservaService.actualizarReservasExpiradas();
        return ResponseEntity.ok().build();
    }
}
