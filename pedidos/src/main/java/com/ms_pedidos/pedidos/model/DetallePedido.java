package com.ms_pedidos.pedidos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "detalle_pedidos")
@Data
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId; // ID del producto en ms-catalog
    private String nombreProducto; // Guardamos el nombre por si se borra del catálogo
    private Integer cantidad;
    private Double precioUnitario; // Precio al momento de la compra

    // Relación inversa: Muchos detalles pertenecen a un Pedido
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonIgnore // Para evitar bucles infinitos al convertir a JSON
    private Pedido pedido;
}