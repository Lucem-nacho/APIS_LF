package com.ms_contacto.contacto.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "contactos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del mensaje", example = "1")
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre de quien envía el mensaje", example = "Juan Pérez")
    private String nombre;

    @Email(message = "Debe ser un email válido")
    @NotBlank(message = "El email es obligatorio")
    @Schema(description = "Correo de contacto", example = "juan.perez@example.com")
    private String email;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Column(length = 1000)
    @Schema(description = "Contenido del mensaje", example = "Hola, me gustaría cotizar un marco de 50x70cm.")
    private String mensaje;

    @Schema(description = "Fecha de envío (se genera automáticamente)", example = "2023-12-01T10:00:00")
    private LocalDateTime fechaEnvio;

    @PrePersist
    public void prePersist() {
        this.fechaEnvio = LocalDateTime.now();
    }
}