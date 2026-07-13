package com.example.Prestamos.Controller;
import com.example.Prestamos.Model.PrestamosModel;
import com.example.Prestamos.Service.PrestamosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/prestamos")
@RequiredArgsConstructor
public class PrestamosController {
    private final PrestamosService prestamoService;

    @GetMapping
    public ResponseEntity<List<PrestamosModel>> getAllPrestamos() {
        return ResponseEntity.ok(prestamoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamosModel> getPrestamoById(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.findById(id));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<PrestamosModel>> getPrestamosActivos() {
        return ResponseEntity.ok(prestamoService.findPrestamosActivos());
    }

    @GetMapping("/atrasados")
    public ResponseEntity<List<PrestamosModel>> getPrestamosAtrasados() {
        return ResponseEntity.ok(prestamoService.findPrestamosAtrasados());
    }

    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<PrestamosModel>> getByLibro(@PathVariable Long libroId) {
        return ResponseEntity.ok(prestamoService.findByLibroId(libroId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PrestamosModel>> getByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(prestamoService.findByUsuarioId(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/activos")
    public ResponseEntity<List<PrestamosModel>> getPrestamosActivosByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(prestamoService.findPrestamosActivosByUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<PrestamosModel> crearPrestamo(@Valid @RequestBody PrestamosModel prestamo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.crear(prestamo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrestamosModel> actualizarPrestamo(
    @PathVariable Long id,
    @Valid @RequestBody PrestamosModel prestamo) {
        return ResponseEntity.ok(prestamoService.actualizar(id, prestamo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable Long id) {
        prestamoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<Void> devolverPrestamo(@PathVariable Long id) {
        prestamoService.devolverPrestamo(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/renovar")
    public ResponseEntity<Void> renovarPrestamo(
            @PathVariable Long id,
            @RequestParam Integer diasExtra) {
        prestamoService.renovarPrestamo(id, diasExtra);
        return ResponseEntity.ok().build();
    }
}
