package com.ms_pedidos.pedidos.dto;
import lombok.Data;

@Data
public class DetalleDto {
    private Long productoId;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
}