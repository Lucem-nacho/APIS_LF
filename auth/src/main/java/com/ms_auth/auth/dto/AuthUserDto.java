package com.ms_auth.auth.dto;
import lombok.Data;

@Data
public class AuthUserDto {
    private String nombre;
    private String apellido;
    private String email; // Necesario para el login
    private String telefono; // Opcional
    private String direccion; // Opcional (Nuevo)
    private String password;
    private String confirmPassword; // Para validar que coincidan
}