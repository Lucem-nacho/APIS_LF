package com.ms_pedidos.pedidos.service;

import com.ms_pedidos.pedidos.dto.DetalleDto;
import com.ms_pedidos.pedidos.dto.PedidoDto;
import com.ms_pedidos.pedidos.model.DetallePedido;
import com.ms_pedidos.pedidos.model.Pedido;
import com.ms_pedidos.pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Crear Pedido (Se mantiene igual)
    public Pedido crearPedido(String emailUsuario, PedidoDto dto) {
        // 1. Descontar Stock
        for (DetalleDto item : dto.getItems()) {
            String url = "http://localhost:8083/api/catalog/productos/" + item.getProductoId() + "/stock?cantidad=" + item.getCantidad();
            try {
                restTemplate.put(url, null);
            } catch (Exception e) {
                throw new RuntimeException("Stock insuficiente para ID: " + item.getProductoId());
            }
        }

        // 2. Guardar Pedido
        Pedido pedido = new Pedido();
        pedido.setUsuarioEmail(emailUsuario);
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");
        
        List<DetallePedido> detalles = new ArrayList<>();
        double totalCalculado = 0.0;

        for (DetalleDto item : dto.getItems()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setProductoId(item.getProductoId());
            detalle.setNombreProducto(item.getNombreProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecioUnitario());
            detalle.setPedido(pedido);

            totalCalculado += (item.getPrecioUnitario() * item.getCantidad());
            detalles.add(detalle);
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(totalCalculado);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> misPedidos(String email) {
        return pedidoRepository.findByUsuarioEmail(email);
    }

    // --- AQUÍ ESTÁ EL CAMBIO IMPORTANTE ---
    // En vez de devolver List<Pedido>, devolvemos una lista de mapas simples.
    // Esto evita CUALQUIER error de recursión o lazy loading.
    public List<Map<String, Object>> obtenerResumenPedidosAdmin() {
        List<Pedido> pedidos = pedidoRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        
        return pedidos.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("usuarioEmail", p.getUsuarioEmail());
            map.put("fechaCreacion", p.getFechaCreacion());
            map.put("estado", p.getEstado());
            map.put("total", p.getTotal());
            // No enviamos los detalles para no sobrecargar la vista principal
            return map;
        }).collect(Collectors.toList());
    }
    
    public Pedido cambiarEstado(Long id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
}