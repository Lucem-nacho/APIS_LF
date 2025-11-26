package com.ms_productos.productos.controller;

import com.ms_productos.productos.model.Categoria; // <--- Importante
import com.ms_productos.productos.model.Producto;
import com.ms_productos.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/catalog")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Listar todos
    @GetMapping("/productos")
    public List<Producto> listarProductos() {
        // Error 1 arreglado: Ahora el servicio sí tiene este método
        return productoService.listarTodos();
    }

    // Crear producto
    @PostMapping("/productos")
    public Producto guardarProducto(@RequestBody Producto producto) {
        // Error 2 y 5 arreglados: Ahora el servicio acepta 'Producto'
        return productoService.guardarProducto(producto);
    }
    
    // Listar categorías
    @GetMapping("/categorias")
    public List<Categoria> listarCategorias() { 
        // Error 3 arreglado: Cambiamos List<Object> por List<Categoria>
        return productoService.listarCategorias(); 
    }

    // Subir imagen (Se mantiene igual)
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) return ResponseEntity.badRequest().body("Archivo vacío");
            
            // Nombre único para evitar colisiones
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename().replace(" ", "_");
            
            // Ruta relativa 'uploads' en la carpeta del proyecto
            Path path = Paths.get("uploads");
            if (!Files.exists(path)) Files.createDirectories(path);
            
            Files.copy(file.getInputStream(), path.resolve(fileName));
            
            // Retornamos la URL pública (necesitas configurar StaticResource en Java para que esto se vea)
            return ResponseEntity.ok("http://localhost:8083/images/" + fileName);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    // Actualizar Stock (Error 4 y 5 arreglados)
    @PutMapping("/productos/{id}/stock")
    public ResponseEntity<String> actualizarStock(@PathVariable Long id, @RequestParam int cantidad) {
        // Ahora el servicio sí tiene obtenerProductoPorId
        Producto producto = productoService.obtenerProductoPorId(id);
        
        if (producto == null) return ResponseEntity.notFound().build();

        int nuevoStock = producto.getStock() - cantidad;
        
        if (nuevoStock < 0) return ResponseEntity.badRequest().body("Stock insuficiente");

        producto.setStock(nuevoStock);
        // Ahora el servicio acepta el objeto Producto correctamente
        productoService.guardarProducto(producto);
        
        return ResponseEntity.ok("Stock actualizado correctamente");
    }

    // MÉTODO NUEVO (ENDPOINT PUT)
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto actualizado = productoService.actualizarProducto(id, producto);
        
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        // Usamos el servicio, que sí está disponible aquí
        boolean eliminado = productoService.borrarProducto(id);
        
        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204 No Content (Éxito)
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found (No existía)
        }
    }
}