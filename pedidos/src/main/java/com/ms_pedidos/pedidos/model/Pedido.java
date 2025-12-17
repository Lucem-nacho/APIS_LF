package com.ms_pedidos.pedidos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del pedido", example = "105")
    private Long id;

    @Schema(description = "Correo del usuario que realizó la compra", example = "cliente@legacyframes.cl")
    private String usuarioEmail;

    @Schema(description = "Fecha y hora de la compra", example = "2023-12-01T14:30:00")
    private LocalDateTime fechaCreacion;

    @Schema(description = "Estado actual del pedido", example = "CONFIRMADO")
    private String estado;

    @Schema(description = "Monto total de la compra", example = "65000.0")
    private Double total;


    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Schema(description = "Lista de productos comprados en este pedido")
    private List<DetallePedido> detalles;
}