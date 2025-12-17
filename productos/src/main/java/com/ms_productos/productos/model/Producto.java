package com.ms_productos.productos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "productos")
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto", example = "10")
    private Long id;

    @Schema(description = "Nombre comercial del producto", example = "Greca Dorada")
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Moldura clásica con acabado dorado brillante")
    private String descripcion;

    @Schema(description = "Precio de venta", example = "15990.0")
    private Double precio;

    @Schema(description = "Unidades disponibles", example = "100")
    private Integer stock;

    @Schema(description = "Ruta de la imagen", example = "/images/greca_dorada.jpg")
    private String imagenUrl;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @Schema(description = "Categoría asignada")
    private Categoria categoria;
}