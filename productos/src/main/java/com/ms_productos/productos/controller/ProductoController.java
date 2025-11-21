package com.ms_productos.productos.controller;

import com.ms_productos.productos.dto.ProductoDto;
import com.ms_productos.productos.model.Categoria;
import com.ms_productos.productos.model.Producto;
import com.ms_productos.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.io.IOException;
import java.util.UUID;

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

    // Endpoint para subir imágenes
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Verificar si el archivo está vacío
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("El archivo está vacío");
            }

            // 2. Generar nombre único (para evitar sobreescribir fotos con el mismo nombre)
            // Ejemplo: "1709999_mifoto.jpg"
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename().replace(" ", "_");
            
            // 3. Crear la ruta de destino (Carpeta "uploads" en la raíz del proyecto)
            Path path = Paths.get("uploads");
            if (!Files.exists(path)) {
                Files.createDirectories(path); // Crea la carpeta si no existe
            }
            
            // 4. Guardar el archivo
            Files.copy(file.getInputStream(), path.resolve(fileName));

            // 5. Devolver la URL pública
            // IMPORTANTE: Cambia "localhost:8083" si despliegas en otro lado
            String imageUrl = "http://localhost:8083/images/" + fileName;
            
            return ResponseEntity.ok(imageUrl);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al subir la imagen: " + e.getMessage());
        }
    }
}