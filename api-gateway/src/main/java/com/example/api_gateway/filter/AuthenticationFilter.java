package com.example.api_gateway.filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/api/v1/usuarios/registrar",
            "/api/v1/usuarios/login",
            "/api/v1/usuarios/email/",
            "/api/v1/libros/disponibles",
            "/api/v1/libros/buscar",
            "/api/v1/categorias/activas"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        boolean isPublicPath = PUBLIC_PATHS.stream().anyMatch(path::startsWith);

        if (isPublicPath) {
            log.info("Ruta pública: {}, sin autenticación", path);
            return chain.filter(exchange);
        }
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Token de autenticación no encontrado o inválido para ruta: {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        String token = authHeader.substring(7);
        if (validarToken(token)) {
            log.info("Token válido para ruta: {}", path);
            return chain.filter(exchange);
        } else {
            log.warn("Token inválido para ruta: {}", path);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private boolean validarToken(String token) {
        return token.length() > 10;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}