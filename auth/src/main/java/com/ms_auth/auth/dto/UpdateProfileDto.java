package com.ms_auth.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateProfileDto {

    @Schema(description = "Nuevo nombre (opcional)", example = "Juan Andrés")
    private String nombre;

    @Schema(description = "Nuevo apellido (opcional)", example = "Pérez")
    private String apellido;

    @Schema(description = "Nuevo teléfono (opcional)", example = "+56911112222")
    private String telefono;

    @Schema(description = "Nueva dirección (opcional)", example = "Pasaje Los Alerces 456")
    private String direccion;

    @Schema(description = "Nueva contraseña (dejar vacío si no se cambia)", example = "nuevaPass123!")
    private String password;

    @Schema(description = "Confirmar nueva contraseña", example = "nuevaPass123!")
    private String confirmPassword;
}