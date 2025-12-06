package com.ms_productos.productos.controller;

import com.ms_productos.productos.model.Producto;
import com.ms_productos.productos.service.ProductoService;
import com.ms_productos.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // Importante para archivos

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/catalog/productos")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoRepository productoRepository;

    // --- LISTAR TODOS ---
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // --- OBTENER POR ID ---
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // --- CREAR PRODUCTO ---
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    // --- ACTUALIZAR PRODUCTO ---
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto detalles) {
        return productoRepository.findById(id)
                .map(prod -> {
                    prod.setNombre(detalles.getNombre());
                    prod.setDescripcion(detalles.getDescripcion());
                    prod.setPrecio(detalles.getPrecio());
                    prod.setStock(detalles.getStock());
                    prod.setImagenUrl(detalles.getImagenUrl());
                    prod.setCategoria(detalles.getCategoria());
                    return ResponseEntity.ok(productoRepository.save(prod));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // --- ELIMINAR PRODUCTO ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        boolean eliminado = productoService.borrarProducto(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- ACTUALIZAR STOCK (Para el microservicio de Pedidos) ---
    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> actualizarStock(@PathVariable Long id, @RequestParam int cantidad) {
        try {
            productoService.descontarStock(id, cantidad);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // --- NUEVO ENDPOINT: SUBIR IMAGEN ---
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Definir carpeta de destino (en la raíz del proyecto productos)
            String folder = "uploads/";
            Path path = Paths.get(folder);
            
            // Crear carpeta si no existe
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            // 2. Generar nombre único para evitar que se sobrescriban archivos con el mismo nombre
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = path.resolve(fileName);

            // 3. Guardar el archivo físico
            Files.copy(file.getInputStream(), filePath);

            // 4. Devolver la URL relativa (que mapeamos en MvcConfig)
            // Ejemplo: /images/mi-foto-uuid.jpg
            String imageUrl = "/images/" + fileName;
            
            return ResponseEntity.ok(imageUrl);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al subir la imagen");
        }
    }
}