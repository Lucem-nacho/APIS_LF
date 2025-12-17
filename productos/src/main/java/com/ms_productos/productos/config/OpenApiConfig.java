package com.ms_productos.productos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Productos API", 
        version = "1.0", 
        description = "Catálogo de productos, control de stock y categorías."
    )
)
public class OpenApiConfig {
}