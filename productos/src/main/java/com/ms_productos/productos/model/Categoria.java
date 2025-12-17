package com.ms_productos.productos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "categorias")
@Data
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la categoría", example = "1")
    private Long id;

    @Schema(description = "Nombre de la categoría", example = "grecas")
    private String nombre;

    @Schema(description = "Descripción de la categoría", example = "Molduras con diseños clásicos y repetitivos")
    private String descripcion;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    @Schema(description = "Lista de productos en esta categoría (oculta para evitar ciclos)")
    private List<Producto> productos;
}