package com.ms_productos.productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductoDto {

    @Schema(description = "Nombre del producto", example = "Marco Rústico")
    private String nombre;

    @Schema(description = "Descripción detallada", example = "Marco de madera de roble envejecido")
    private String descripcion;

    @Schema(description = "Precio unitario en CLP", example = "25000.0")
    private Double precio;

    @Schema(description = "Cantidad disponible en inventario", example = "50")
    private Integer stock;

    @Schema(description = "URL de la imagen (debe ser ruta relativa /images/...)", example = "/images/marco_rustico.jpg")
    private String imagenUrl;

    @Schema(description = "ID de la categoría a la que pertenece", example = "2")
    private Long categoriaId;
}