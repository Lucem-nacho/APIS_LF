package com.ms_pedidos.pedidos.service;

import com.ms_pedidos.pedidos.dto.DetalleDto;
import com.ms_pedidos.pedidos.dto.PedidoDto;
import com.ms_pedidos.pedidos.model.DetallePedido;
import com.ms_pedidos.pedidos.model.Pedido;
import com.ms_pedidos.pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; // <--- Importante

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // Inyectamos la herramienta para comunicarnos con otros microservicios
    @Autowired
    private RestTemplate restTemplate; 

    public Pedido crearPedido(String email, PedidoDto dto) {
        
        // 1. Crear el Pedido Padre
        Pedido pedido = new Pedido();
        pedido.setUsuarioEmail(email);
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setEstado("CONFIRMADO");

        List<DetallePedido> detallesEntidad = new ArrayList<>();
        double totalCalculado = 0.0;

        if (dto.getItems() != null) {
            for (DetalleDto itemDto : dto.getItems()) {
                
                // --- NUEVO: COMUNICACIÓN CON MICROSERVICIO PRODUCTOS ---
                // Llamamos a la API de Productos para descontar el stock
                // URL: http://localhost:8083/api/catalog/productos/{id}/stock?cantidad={n}
                String urlProductos = "http://localhost:8083/api/catalog/productos/" 
                                    + itemDto.getProductoId() 
                                    + "/stock?cantidad=" + itemDto.getCantidad();
                
                try {
                    // El método put ejecuta la petición PUT
                    restTemplate.put(urlProductos, null);
                } catch (Exception e) {
                    // Si falla (ej: sin stock), lanzamos error y se cancela todo el pedido
                    throw new RuntimeException("Error al procesar el producto ID " + itemDto.getProductoId() + ": Stock insuficiente o servicio no disponible.");
                }
                // -------------------------------------------------------

                DetallePedido detalle = new DetallePedido();
                detalle.setProductoId(itemDto.getProductoId());
                detalle.setNombreProducto(itemDto.getNombreProducto());
                detalle.setCantidad(itemDto.getCantidad());
                detalle.setPrecioUnitario(itemDto.getPrecioUnitario());
                
                detalle.setUsuarioEmail(email); // Corrección anterior
                detalle.setPedido(pedido);
                
                detallesEntidad.add(detalle);
                totalCalculado += (itemDto.getPrecioUnitario() * itemDto.getCantidad());
            }
        }

        pedido.setDetalles(detallesEntidad);
        pedido.setTotal(totalCalculado);

        return pedidoRepository.save(pedido);
    }
}