package com.example.orders.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("Order Management API")
                .version("v1")
                .description("API para gestión de órdenes (perfiles, validación y manejo de errores).")
                .contact(new Contact().name("Digital NAO").email("team@digitalnao.example")));
    }
}
