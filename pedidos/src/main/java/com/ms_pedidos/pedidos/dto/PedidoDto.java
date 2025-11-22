package com.ms_pedidos.pedidos.dto;
import lombok.Data;
import java.util.List;

@Data
public class PedidoDto {
    // No necesitamos enviar el total, lo calculamos en el backend por seguridad
    private List<DetalleDto> items;
}