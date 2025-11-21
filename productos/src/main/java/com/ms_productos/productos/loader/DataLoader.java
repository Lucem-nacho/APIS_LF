package com.ms_productos.productos.loader;

import com.ms_productos.productos.model.Categoria;
import com.ms_productos.productos.model.Producto;
import com.ms_productos.productos.repository.CategoriaRepository;
import com.ms_productos.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        cargarDatos();
    }

    private void cargarDatos() {
        // 1. Verificar si ya existen datos para no duplicar
        if (categoriaRepository.count() == 0) {
            
            System.out.println(">>> CARGANDO DATOS INICIALES EN LA BASE DE DATOS... <<<");

            // --- CREAR CATEGORÍAS ---
            Categoria grecas = new Categoria();
            grecas.setNombre("grecas");
            grecas.setDescripcion("Molduras con diseños clásicos");

            Categoria rusticas = new Categoria();
            rusticas.setNombre("rusticas");
            rusticas.setDescripcion("Estilo madera envejecida");

            Categoria nativas = new Categoria();
            nativas.setNombre("nativas");
            nativas.setDescripcion("Maderas nobles chilenas");

            Categoria finger = new Categoria();
            finger.setNombre("finger-joint");
            finger.setDescripcion("Unión dentada resistente");

            // Guardamos las categorías y las recuperamos para obtener sus IDs generados
            List<Categoria> categoriasGuardadas = categoriaRepository.saveAll(Arrays.asList(grecas, rusticas, nativas, finger));
            
            // Recuperamos las referencias guardadas (con ID)
            Categoria grecasSaved = categoriasGuardadas.get(0);
            Categoria rusticasSaved = categoriasGuardadas.get(1);
            Categoria nativasSaved = categoriasGuardadas.get(2);
            Categoria fingerSaved = categoriasGuardadas.get(3);

            // --- CREAR PRODUCTOS ---
            
            Producto p1 = new Producto();
            p1.setNombre("Moldura Greca Dorada");
            p1.setDescripcion("Hermosa moldura con diseño clásico.");
            p1.setPrecio(20000.0);
            p1.setStock(50);
            p1.setImagenUrl("/assets/moldura3.jpg");
            p1.setCategoria(grecasSaved);

            Producto p2 = new Producto();
            p2.setNombre("Roble Rústico");
            p2.setDescripcion("Ideal para ambientes cálidos y tradicionales.");
            p2.setPrecio(22000.0);
            p2.setStock(30);
            p2.setImagenUrl("/assets/rustica1.jpg");
            p2.setCategoria(rusticasSaved);

            Producto p3 = new Producto();
            p3.setNombre("Mañío Chileno");
            p3.setDescripcion("Madera nativa de alta calidad y durabilidad.");
            p3.setPrecio(25000.0);
            p3.setStock(15);
            p3.setImagenUrl("/assets/nativas1.jpg");
            p3.setCategoria(nativasSaved);

            Producto p4 = new Producto();
            p4.setNombre("Pino Finger Joint");
            p4.setDescripcion("Económico y resistente, sin nudos.");
            p4.setPrecio(18000.0);
            p4.setStock(100);
            p4.setImagenUrl("/assets/finger_joint1.jpg");
            p4.setCategoria(fingerSaved);

            // Guardamos todos los productos
            productoRepository.saveAll(Arrays.asList(p1, p2, p3, p4));

            System.out.println(">>> ¡DATOS CARGADOS EXITOSAMENTE! <<<");
        } else {
            System.out.println(">>> La base de datos ya tiene datos. No se requiere carga inicial. <<<");
        }
    }
}