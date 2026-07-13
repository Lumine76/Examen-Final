package com.example.api_gateway.filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestId = UUID.randomUUID().toString();
        log.info("=== REQUEST ID: {} ===", requestId);
        log.info("Method: {}", exchange.getRequest().getMethod());
        log.info("Path: {}", exchange.getRequest().getURI().getPath());
        log.info("Query Params: {}", exchange.getRequest().getQueryParams());
        log.info("Headers: {}", exchange.getRequest().getHeaders().keySet());
        exchange.getRequest().mutate()
                .header("X-Request-ID", requestId)
                .build();

        long startTime = System.currentTimeMillis();

        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    long duration = System.currentTimeMillis() - startTime;
                    log.info("Response Status: {}", exchange.getResponse().getStatusCode());
                    log.info("Duration: {} ms", duration);
                    log.info("=== END REQUEST: {} ===", requestId);
                })
        );
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
