package com.ms_auth.auth.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefono;
    private String direccion;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "rol_id")
    private Rol rol; 
}