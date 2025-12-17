package com.ms_auth.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del rol", example = "1")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Nombre del rol en el sistema", example = "ROLE_USER")
    private String nombre; 
}