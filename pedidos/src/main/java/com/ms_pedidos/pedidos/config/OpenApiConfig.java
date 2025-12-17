package com.ms_pedidos.pedidos.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Pedidos API", 
        version = "1.0", 
        description = "Gestión de órdenes de compra y detalles de productos."
    )
)
public class OpenApiConfig {
}