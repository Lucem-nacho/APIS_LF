package com.ms_pedidos.pedidos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
public class PedidoDto {

    @Schema(description = "Lista de productos incluidos en el pedido")
    private List<DetalleDto> items;
}