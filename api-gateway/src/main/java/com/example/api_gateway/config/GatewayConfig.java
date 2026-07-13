package com.example.api_gateway.config;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
.route("servicio-libros", r -> r
.path("/api/v1/libros/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("librosCB")
                                        .setFallbackUri("forward:/fallback/libros"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE,
                                                org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)).addRequestHeader("X-Gateway", "Spring-Cloud-Gateway")).uri("http://localhost:8081")).route("servicio-autores", r -> r
                        .path("/api/v1/autores/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("autoresCB")
                                        .setFallbackUri("forward:/fallback/autores"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE))
                                .addRequestHeader("X-Gateway", "Spring-Cloud-Gateway"))
                        .uri("http://localhost:8082"))
                .route("servicio-usuarios", r -> r
                        .path("/api/v1/usuarios/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("usuariosCB")
                                        .setFallbackUri("forward:/fallback/usuarios"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE))
                                .addRequestHeader("X-Gateway", "Spring-Cloud-Gateway"))
                        .uri("http://localhost:8083"))
                .route("servicio-prestamos", r -> r
                        .path("/api/v1/prestamos/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("prestamosCB")
                                        .setFallbackUri("forward:/fallback/prestamos"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE))
                                .addRequestHeader("X-Gateway", "Spring-Cloud-Gateway"))
                        .uri("http://localhost:8084"))
                .route("servicio-reservas", r -> r
                        .path("/api/v1/reservas/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("reservasCB")
                                        .setFallbackUri("forward:/fallback/reservas"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE))
                                .addRequestHeader("X-Gateway", "Spring-Cloud-Gateway"))
                        .uri("http://localhost:8085"))
                .route("servicio-categorias", r -> r
                        .path("/api/v1/categorias/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("categoriasCB")
                                        .setFallbackUri("forward:/fallback/categorias"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE))
                                .addRequestHeader("X-Gateway", "Spring-Cloud-Gateway"))
                        .uri("http://localhost:8086"))
                .route("servicio-resenas", r -> r
                        .path("/api/v1/resenas/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("resenasCB")
                                        .setFallbackUri("forward:/fallback/resenas"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE))
                                .addRequestHeader("X-Gateway", "Spring-Cloud-Gateway"))
                        .uri("http://localhost:8087"))
                .route("servicio-notificaciones", r -> r
                        .path("/api/v1/notificaciones/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("notificacionesCB")
                                        .setFallbackUri("forward:/fallback/notificaciones"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE))
                                .addRequestHeader("X-Gateway", "Spring-Cloud-Gateway"))
                        .uri("http://localhost:8088"))
                .route("servicio-inventario", r -> r
                        .path("/api/v1/inventario/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("inventarioCB")
                                        .setFallbackUri("forward:/fallback/inventario"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE))
                                .addRequestHeader("X-Gateway", "Spring-Cloud-Gateway"))
                        .uri("http://localhost:8089"))

                .build();
    }
}
