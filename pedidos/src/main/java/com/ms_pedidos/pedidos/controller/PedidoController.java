package com.ms_pedidos.pedidos.controller;

import com.ms_pedidos.pedidos.model.Pedido;
import com.ms_pedidos.pedidos.repository.PedidoRepository;
import com.ms_pedidos.pedidos.service.PedidoService;
import com.ms_pedidos.pedidos.dto.PedidoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173") 
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    // Crear pedido
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody PedidoDto pedidoDto, @RequestParam String email) {
        // --- CORRECCIÓN AQUÍ: CAMBIAMOS EL ORDEN (Primero email, luego dto) ---
        Pedido nuevoPedido = pedidoService.crearPedido(email, pedidoDto); 
        return ResponseEntity.ok(nuevoPedido);
    }

    // Lista (Admin)
    @GetMapping("/admin/all")
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoRepository.findAll());
    }

    // Historial del cliente
    @GetMapping("/my-orders")
    public ResponseEntity<List<Pedido>> misPedidos(@RequestParam String email) {
        List<Pedido> historial = pedidoRepository.findByUsuarioEmail(email);
        return ResponseEntity.ok(historial);
    }
}