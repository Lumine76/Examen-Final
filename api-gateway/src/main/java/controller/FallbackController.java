package controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/libros")
    public ResponseEntity<Map<String, Object>> librosFallback() {
        return crearFallbackResponse("Servicio de Libros no disponible");
    }

    @GetMapping("/autores")
    public ResponseEntity<Map<String, Object>> autoresFallback() {
        return crearFallbackResponse("Servicio de Autores no disponible");
    }

    @GetMapping("/usuarios")
    public ResponseEntity<Map<String, Object>> usuariosFallback() {
        return crearFallbackResponse("Servicio de Usuarios no disponible");
    }

    @GetMapping("/prestamos")
    public ResponseEntity<Map<String, Object>> prestamosFallback() {
        return crearFallbackResponse("Servicio de Préstamos no disponible");
    }

    @GetMapping("/reservas")
    public ResponseEntity<Map<String, Object>> reservasFallback() {
        return crearFallbackResponse("Servicio de Reservas no disponible");
    }

    @GetMapping("/categorias")
    public ResponseEntity<Map<String, Object>> categoriasFallback() {
        return crearFallbackResponse("Servicio de Categorías no disponible");
    }

    @GetMapping("/resenas")
    public ResponseEntity<Map<String, Object>> resenasFallback() {
        return crearFallbackResponse("Servicio de Reseñas no disponible");
    }

    @GetMapping("/notificaciones")
    public ResponseEntity<Map<String, Object>> notificacionesFallback() {
        return crearFallbackResponse("Servicio de Notificaciones no disponible");
    }

    @GetMapping("/inventario")
    public ResponseEntity<Map<String, Object>> inventarioFallback() {
        return crearFallbackResponse("Servicio de Inventario no disponible");
    }

    private ResponseEntity<Map<String, Object>> crearFallbackResponse(String mensaje) {
        log.warn("Fallback activado: {}", mensaje);

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        response.put("error", "Servicio no disponible");
        response.put("message", mensaje);
        response.put("path", "/fallback");

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}