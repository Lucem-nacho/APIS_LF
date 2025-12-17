package com.ms_pedidos.pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DetalleDto {

    @Schema(description = "ID del producto a comprar", example = "1")
    private Long productoId;

    @Schema(description = "Nombre del producto (para referencia)", example = "Marco Dorado Clásico")
    private String nombreProducto;

    @Schema(description = "Cantidad a comprar", example = "2")
    private Integer cantidad;

    @Schema(description = "Precio unitario al momento de la compra", example = "32500.0")
    private Double precioUnitario;
}