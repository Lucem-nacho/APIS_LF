package com.ms_pedidos.pedidos.service;

import com.ms_pedidos.pedidos.dto.DetalleDto;
import com.ms_pedidos.pedidos.dto.PedidoDto;
import com.ms_pedidos.pedidos.model.DetallePedido;
import com.ms_pedidos.pedidos.model.Pedido;
import com.ms_pedidos.pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // Crear Orden
    public Pedido crearPedido(String emailUsuario, PedidoDto dto) {
        Pedido pedido = new Pedido();
        pedido.setUsuarioEmail(emailUsuario);
        
        List<DetallePedido> detalles = new ArrayList<>();
        double totalCalculado = 0.0;

        // Recorremos los items que vienen del carrito
        for (DetalleDto item : dto.getItems()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setProductoId(item.getProductoId());
            detalle.setNombreProducto(item.getNombreProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecioUnitario());
            detalle.setPedido(pedido); // Vinculamos con el padre

            totalCalculado += (item.getPrecioUnitario() * item.getCantidad());
            detalles.add(detalle);
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(totalCalculado);

        return pedidoRepository.save(pedido);
    }

    // Listar mis pedidos (Usuario)
    public List<Pedido> misPedidos(String email) {
        return pedidoRepository.findByUsuarioEmail(email);
    }

    // Listar TODOS (Admin)
    public List<Pedido> todosLosPedidos() {
        return pedidoRepository.findAll();
    }
    
    // Obtener un pedido por ID
    public Pedido obtenerPedido(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    // Cambiar estado (Admin)
    public Pedido cambiarEstado(Long id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
}