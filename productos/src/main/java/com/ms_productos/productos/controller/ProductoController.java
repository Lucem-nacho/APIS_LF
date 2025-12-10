package com.ms_productos.productos.controller;

import com.ms_productos.productos.model.Producto;
import com.ms_productos.productos.service.ProductoService;
import com.ms_productos.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        boolean eliminado = productoService.borrarProducto(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> actualizarStock(@PathVariable Long id, @RequestParam int cantidad) {
        try {
            productoService.descontarStock(id, cantidad);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // --- ENDPOINT MODIFICADO: SUBIDA A PRUEBA DE FALLOS ---
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Lógica inteligente para detectar la carpeta
            String folder = "uploads/";
            String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();

            // Si no estamos dentro de "productos", ajustamos la ruta
            if (!currentDir.endsWith("productos")) {
                folder = "productos/uploads/";
            }

            Path path = Paths.get(folder);
            
            // 2. Crear carpeta si no existe
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println(">>> Carpeta creada en: " + path.toAbsolutePath());
            }

            // 3. Guardar archivo
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = path.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);

            // 4. Retornar URL pública
            String imageUrl = "/images/" + fileName;
            return ResponseEntity.ok(imageUrl);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al subir la imagen");
        }
    }
}