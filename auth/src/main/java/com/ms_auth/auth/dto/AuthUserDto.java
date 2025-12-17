package com.ms_auth.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthUserDto {

    @Schema(description = "Nombre del usuario", example = "Juan")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "Pérez")
    private String apellido;

    @Schema(description = "Correo electrónico único", example = "juan.perez@legacyframes.cl")
    private String email;

    @Schema(description = "Teléfono de contacto", example = "+56912345678")
    private String telefono;

    @Schema(description = "Dirección de despacho", example = "Av. Siempre Viva 742, Santiago")
    private String direccion;

    @Schema(description = "Contraseña segura", example = "password123")
    private String password;

    @Schema(description = "Confirmación de contraseña (debe coincidir)", example = "password123")
    private String confirmPassword;
}