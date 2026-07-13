package com.example.Reservas.exception;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Servicio de Reservas API")
                        .version("1.0.0")
                        .description("Microservicio para la gestión de reservas de libros en el sistema de librería")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}