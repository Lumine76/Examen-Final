package com.example.Categorias.Controller;
import com.example.Categorias.Model.Categoria;
import com.example.Categorias.Service.CategoriasService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriasController {

    private final CategoriasService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> getAllCategorias() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Categoria> getCategoriaByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(categoriaService.findByNombre(nombre));
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Categoria>> getCategoriasActivas() {
        return ResponseEntity.ok(categoriaService.findCategoriasActivas());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Categoria>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(categoriaService.buscarPorNombre(nombre));
    }

    @GetMapping("/{id}/count-libros")
    public ResponseEntity<Long> countLibrosByCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.countLibrosByCategoria(id));
    }

    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@Valid @RequestBody Categoria categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crear(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody Categoria categoria) {
        return ResponseEntity.ok(categoriaService.actualizar(id, categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activarCategoria(@PathVariable Long id) {
        categoriaService.activarCategoria(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarCategoria(@PathVariable Long id) {
        categoriaService.desactivarCategoria(id);
        return ResponseEntity.ok().build();
    }
}
