package com.ms_pedidos.pedidos.controller;

import com.ms_pedidos.pedidos.dto.PedidoDto;
import com.ms_pedidos.pedidos.model.Pedido;
import com.ms_pedidos.pedidos.service.PedidoService;
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

    // 1. Crear Pedido (Cualquier usuario autenticado)
    // NOTA: En un escenario real, sacaríamos el email del Token.
    // Por simplicidad ahora, lo pedimos como parámetro o asumimos que el frontend lo manda.
    // Para hacerlo PRO, deberías implementar JwtFilter aquí también, 
    // pero por ahora lo pasaremos como parámetro simulando el token.
    @PostMapping
    public ResponseEntity<Pedido> crear(@RequestParam String email, @RequestBody PedidoDto dto) {
        return ResponseEntity.ok(pedidoService.crearPedido(email, dto));
    }

    // 2. Historial de un usuario
    @GetMapping("/my-orders")
    public ResponseEntity<List<Pedido>> historial(@RequestParam String email) {
        return ResponseEntity.ok(pedidoService.misPedidos(email));
    }

    // 3. Listar TODOs  (Solo Admin debería poder)
    @GetMapping("/admin/all")
    public ResponseEntity<List<Pedido>> todos() {
        return ResponseEntity.ok(pedidoService.todosLosPedidos());
    }

    // 4. Cambiar estado (Admin)
    @PutMapping("/{id}/status")
    public ResponseEntity<Pedido> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(pedidoService.cambiarEstado(id, estado));
    }
}