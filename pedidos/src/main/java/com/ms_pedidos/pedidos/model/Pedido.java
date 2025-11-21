package com.ms_pedidos.pedidos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Guardamos el email del usuario para saber de quién es
    // (Viene del Token en el frontend)
    private String usuarioEmail; 

    private LocalDateTime fechaCreacion;

    private String estado; // Ej: "PENDIENTE", "PAGADO", "ENVIADO", "ENTREGADO"
    
    private Double total;

    // Relación: Un Pedido tiene muchos Detalles
    // cascade = ALL significa que si guardo el Pedido, se guardan sus detalles solos
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetallePedido> detalles;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estado == null) estado = "PENDIENTE";
    }
}