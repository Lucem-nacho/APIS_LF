package com.ms_pedidos.pedidos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode; 
import lombok.ToString;

@Entity
@Table(name = "detalle_pedidos")
@Data
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la línea de detalle", example = "501")
    private Long id;

    @Schema(description = "ID del producto original", example = "1")
    private Long productoId;

    @Schema(description = "Nombre del producto al momento de la compra", example = "Marco Dorado Clásico")
    private String nombreProducto;

    @Schema(description = "Cantidad comprada", example = "2")
    private Integer cantidad;

    @Schema(description = "Precio unitario al momento de la compra", example = "32500.0")
    private Double precioUnitario;

    @Column(name = "usuario_email")
    @Schema(description = "Email del comprador", example = "cliente@legacyframes.cl")
    private String usuarioEmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    @JsonBackReference
    @ToString.Exclude           
    @EqualsAndHashCode.Exclude  
    @Schema(description = "Referencia al pedido padre")
    private Pedido pedido;
}