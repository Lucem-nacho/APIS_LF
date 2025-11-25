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
        // Solo cargamos si la base de datos está vacía
        if (categoriaRepository.count() == 0) {
            
            System.out.println(">>> CARGANDO DATOS AUTOMÁTICOS DE MOLDURAS Y CUADROS... <<<");

            // 1. CREAR CATEGORÍAS
            Categoria grecas = new Categoria(); grecas.setNombre("grecas"); grecas.setDescripcion("Diseños clásicos");
            Categoria rusticas = new Categoria(); rusticas.setNombre("rusticas"); rusticas.setDescripcion("Madera envejecida");
            Categoria nativas = new Categoria(); nativas.setNombre("nativas"); nativas.setDescripcion("Maderas chilenas");
            Categoria finger = new Categoria(); finger.setNombre("finger-joint"); finger.setDescripcion("Unión dentada");
            
            // NUEVA CATEGORÍA: CUADROS
            Categoria cuadros = new Categoria(); cuadros.setNombre("cuadros"); cuadros.setDescripcion("Marcos listos para colgar");

            // Guardamos y recuperamos para tener los IDs (El orden importa: Cuadros será el ID 5)
            List<Categoria> cats = categoriaRepository.saveAll(Arrays.asList(grecas, rusticas, nativas, finger, cuadros));
            
            Categoria catGrecas = cats.get(0);
            Categoria catRusticas = cats.get(1);
            Categoria catNativas = cats.get(2);
            Categoria catFinger = cats.get(3);
            Categoria catCuadros = cats.get(4); // ID 5

            // 2. CREAR MOLDURAS (Ejemplos)
            Producto m1 = crearProd("Moldura Greca Dorada", "Diseño clásico", 20000.0, 50, "/assets/moldura3.jpg", catGrecas);
            Producto m2 = crearProd("Roble Rústico", "Ambientes cálidos", 22000.0, 30, "/assets/rustica1.jpg", catRusticas);
            Producto m3 = crearProd("Mañío Chileno", "Madera nativa", 25000.0, 15, "/assets/nativas1.jpg", catNativas);
            Producto m4 = crearProd("Pino Finger Joint", "Económico", 18000.0, 100, "/assets/finger_joint1.jpg", catFinger);

            // 3. CREAR CUADROS (Tus productos reales)
            Producto c1 = crearProd("Set de Marcos Familiares", "Conjunto de marcos negros ideales para collage.", 45000.0, 20, "/assets/Frame Picture.png", catCuadros);
            Producto c2 = crearProd("Marco Minimalista Moderno", "Marco delgado de aluminio negro.", 18990.0, 30, "/assets/fameproxima.png", catCuadros);
            Producto c3 = crearProd("Marco Dorado Clásico", "Elegancia pura con acabado envejecido.", 32500.0, 15, "/assets/marcoDoradoClasico.png", catCuadros);
            Producto c4 = crearProd("Marco Madera Rústica", "Madera natural sin tratar estilo boho.", 24990.0, 10, "/assets/marcoRustico.png", catCuadros);
            Producto c5 = crearProd("Marco para Diploma", "Diseño sobrio para certificados.", 15000.0, 40, "/assets/marco.diplomaa.png", catCuadros);
            Producto c6 = crearProd("Marco Vintage Tallado", "Marco ancho con detalles tallados a mano.", 55000.0, 5, "/assets/marcoantigo.png", catCuadros);

            // Guardar todo
            productoRepository.saveAll(Arrays.asList(m1, m2, m3, m4, c1, c2, c3, c4, c5, c6));

            System.out.println(">>> ¡BASE DE DATOS LLENA Y LISTA PARA VENDER! <<<");
        }
    }

    // Método auxiliar para no repetir código
    private Producto crearProd(String nombre, String desc, Double precio, Integer stock, String img, Categoria cat) {
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setDescripcion(desc);
        p.setPrecio(precio);
        p.setStock(stock);
        p.setImagenUrl(img);
        p.setCategoria(cat);
        return p;
    }
}