package com.ms_contacto.contacto.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Contacto API", 
        version = "1.0", 
        description = "Gestión de formularios de contacto y mensajes de clientes."
    )
)
public class OpenApiConfig {
}