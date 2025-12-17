package com.ms_auth.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserProfileDto {

    @Schema(description = "Nombre del usuario", example = "María")
    private String nombre;

    @Schema(description = "Apellido del usuario", example = "González")
    private String apellido;

    @Schema(description = "Correo electrónico registrado", example = "maria.gonzalez@email.com")
    private String email;

    @Schema(description = "Teléfono de contacto", example = "+56987654321")
    private String telefono;

    @Schema(description = "Dirección registrada", example = "Calle Nueva 123, Providencia")
    private String direccion;
}