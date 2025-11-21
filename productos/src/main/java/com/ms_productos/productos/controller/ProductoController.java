package com.ms_productos.productos.controller;

import com.ms_productos.productos.dto.ProductoDto;
import com.ms_productos.productos.model.Categoria;
import com.ms_productos.productos.model.Producto;
import com.ms_productos.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@CrossOrigin(origins = "http://localhost:5173") // Permite conexión desde React
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // --- CATEGORÍAS ---
    @PostMapping("/categorias")
    public ResponseEntity<Categoria> crearCat(@RequestBody Categoria cat) {
        return ResponseEntity.ok(productoService.crearCategoria(cat));
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> listarCat() {
        return ResponseEntity.ok(productoService.listarCategorias());
    }

    // --- PRODUCTOS ---
    @PostMapping("/productos")
    public ResponseEntity<Producto> crearProd(@RequestBody ProductoDto dto) {
        return ResponseEntity.ok(productoService.guardarProducto(dto));
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> listarProd() {
        return ResponseEntity.ok(productoService.listarProductos());
    }
    
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProd(@PathVariable Long id) {
         return productoService.obtenerPorId(id)
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> updateProd(@PathVariable Long id, @RequestBody ProductoDto dto) {
        return ResponseEntity.ok(productoService.modificarProducto(id, dto));
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> deleteProd(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}