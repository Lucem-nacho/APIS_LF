package com.ms_pedidos.pedidos.controller;

import com.ms_pedidos.pedidos.dto.PedidoDto;
import com.ms_pedidos.pedidos.model.Pedido;
import com.ms_pedidos.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
// CORS global ya está configurado en el Application.java, pero esto no daña.
@CrossOrigin(origins = "http://localhost:5173") 
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestParam String email, @RequestBody PedidoDto pedidoDto) {
        return ResponseEntity.ok(pedidoService.crearPedido(email, pedidoDto));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<Pedido>> misPedidos(@RequestParam String email) {
        return ResponseEntity.ok(pedidoService.misPedidos(email));
    }

    // --- MÉTODO DEL ADMIN ACTUALIZADO ---
    // Ahora devuelve List<Map<String, Object>> en vez de Entidades complejas
    @GetMapping("/admin/all")
    public ResponseEntity<List<Map<String, Object>>> listarTodos() {
        return ResponseEntity.ok(pedidoService.obtenerResumenPedidosAdmin());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Pedido> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(pedidoService.cambiarEstado(id, estado));
    }
}