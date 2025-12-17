package com.ms_auth.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID autogenerado del usuario", example = "10")
    private Long id;

    @Schema(description = "Nombre real", example = "Pedro")
    private String nombre;

    @Schema(description = "Apellido real", example = "Sánchez")
    private String apellido;

    @Column(unique = true, nullable = false)
    @Schema(description = "Email único (username)", example = "pedro@legacyframes.cl")
    private String email;

    @Schema(description = "Teléfono móvil o fijo", example = "22334455")
    private String telefono;
    
    @Schema(description = "Dirección completa", example = "Av. Libertador 1234")
    private String direccion;

    @Column(nullable = false)
    @Schema(description = "Contraseña encriptada (BCrypt)", example = "$2a$10$EixZaYVK1fsdf...")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    @Schema(description = "Rol asignado al usuario")
    private Rol rol; 
}