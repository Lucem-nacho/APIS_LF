package com.ms_auth.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema; // Importante para Swagger
import lombok.Data;

@Data
public class LoginDto {

    @Schema(
        description = "Correo electrónico del usuario registrado", 
        example = "juan.perez@email.com"
    )
    private String email;

    @Schema(
        description = "Contraseña del usuario", 
        example = "123456"
    )
    private String password;
}