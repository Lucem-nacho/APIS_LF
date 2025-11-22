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

            Categoria naturales = new Categoria();
            naturales.setNombre("naturales");
            naturales.setDescripcion("Molduras naturales con textura de madera");

            Categoria cuadros = new Categoria();
            cuadros.setNombre("cuadros");
            cuadros.setDescripcion("Marcos y cuadros decorativos");

            // Guardamos las categorías y las recuperamos para obtener sus IDs generados
            List<Categoria> categoriasGuardadas = categoriaRepository.saveAll(Arrays.asList(grecas, rusticas, nativas, finger, naturales, cuadros));
            
            // Recuperamos las referencias guardadas (con ID)
            Categoria grecasSaved = categoriasGuardadas.get(0);
            Categoria rusticasSaved = categoriasGuardadas.get(1);
            Categoria nativasSaved = categoriasGuardadas.get(2);
            Categoria fingerSaved = categoriasGuardadas.get(3);
            Categoria naturalesSaved = categoriasGuardadas.get(4);
            Categoria cuadrosSaved = categoriasGuardadas.get(5);

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

            // GRECAS
            Producto grecaZo = new Producto();
            grecaZo.setNombre("I 09 greca zo");
            grecaZo.setDescripcion("Elegante greca decorativa con diseño tradicional ZO. Ideal para marcos clásicos.");
            grecaZo.setPrecio(20000.0);
            grecaZo.setStock(50);
            grecaZo.setImagenUrl("/assets/moldura3.jpg");
            grecaZo.setCategoria(grecasSaved);

            Producto grecaCorazon = new Producto();
            grecaCorazon.setNombre("I 09 greca corazón");
            grecaCorazon.setDescripcion("Hermosa greca con motivo de corazón, perfecta para marcos románticos.");
            grecaCorazon.setPrecio(20000.0);
            grecaCorazon.setStock(45);
            grecaCorazon.setImagenUrl("/assets/moldura4.jpg");
            grecaCorazon.setCategoria(grecasSaved);

            Producto grecaOro = new Producto();
            grecaOro.setNombre("P 15 greca LA oro");
            grecaOro.setDescripcion("Greca con acabado dorado, elegante y sofisticada.");
            grecaOro.setPrecio(20000.0);
            grecaOro.setStock(30);
            grecaOro.setImagenUrl("/assets/moldura3.jpg");
            grecaOro.setCategoria(grecasSaved);

            Producto grecaPlata = new Producto();
            grecaPlata.setNombre("P 15 greca LA plata");
            grecaPlata.setDescripcion("Greca con acabado plateado, moderna y elegante.");
            grecaPlata.setPrecio(20000.0);
            grecaPlata.setStock(35);
            grecaPlata.setImagenUrl("/assets/moldura4.jpg");
            grecaPlata.setCategoria(grecasSaved);

            // RÚSTICAS
            Producto rusticaAzul = new Producto();
            rusticaAzul.setNombre("H 20 albayalde azul");
            rusticaAzul.setDescripcion("Moldura rústica con acabado albayalde azul, ideal para ambientes campestres.");
            rusticaAzul.setPrecio(20000.0);
            rusticaAzul.setStock(25);
            rusticaAzul.setImagenUrl("/assets/rustica1.jpg");
            rusticaAzul.setCategoria(rusticasSaved);

            // NATURALES
            Producto naturalAlerce = new Producto();
            naturalAlerce.setNombre("B-10 t/alerce");
            naturalAlerce.setDescripcion("Moldura natural de alerce con textura original de la madera.");
            naturalAlerce.setPrecio(20000.0);
            naturalAlerce.setStock(20);
            naturalAlerce.setImagenUrl("/assets/naturales1.jpg");
            naturalAlerce.setCategoria(naturalesSaved);

            // NATIVAS
            Producto nativaJ16 = new Producto();
            nativaJ16.setNombre("J-16");
            nativaJ16.setDescripcion("Moldura de madera nativa chilena, resistente y de gran calidad.");
            nativaJ16.setPrecio(20000.0);
            nativaJ16.setStock(15);
            nativaJ16.setImagenUrl("/assets/nativas1.jpg");
            nativaJ16.setCategoria(nativasSaved);

            // FINGER JOINT
            Producto fingerP12 = new Producto();
            fingerP12.setNombre("P-12 Finger Joint");
            fingerP12.setDescripcion("Moldura finger joint de alta calidad con unión invisible.");
            fingerP12.setPrecio(20000.0);
            fingerP12.setStock(100);
            fingerP12.setImagenUrl("/assets/finger_joint1.jpg");
            fingerP12.setCategoria(fingerSaved);

            // CUADROS
            Producto cuadroDorado = new Producto();
            cuadroDorado.setNombre("Marco Clásico Dorado");
            cuadroDorado.setDescripcion("Marco elegante con acabado dorado, ideal para obras de arte y fotografías especiales.");
            cuadroDorado.setPrecio(25000.0);
            cuadroDorado.setStock(20);
            cuadroDorado.setImagenUrl("/assets/marcoDoradoClasico.png");
            cuadroDorado.setCategoria(cuadrosSaved);

            Producto cuadroMinimalista = new Producto();
            cuadroMinimalista.setNombre("Marco Moderno Minimalista");
            cuadroMinimalista.setDescripcion("Diseño contemporáneo y limpio, perfecto para espacios modernos y fotografías actuales.");
            cuadroMinimalista.setPrecio(18000.0);
            cuadroMinimalista.setStock(30);
            cuadroMinimalista.setImagenUrl("/assets/marco-minimalista-ambiente-moderno_23-2149301885.jpg");
            cuadroMinimalista.setCategoria(cuadrosSaved);

            Producto cuadroRustico = new Producto();
            cuadroRustico.setNombre("Marco Rústico de Madera");
            cuadroRustico.setDescripcion("Acabado rústico natural, ideal para ambientes campestres y fotografías de naturaleza.");
            cuadroRustico.setPrecio(22000.0);
            cuadroRustico.setStock(25);
            cuadroRustico.setImagenUrl("/assets/marcoRustico.png");
            cuadroRustico.setCategoria(cuadrosSaved);

            Producto cuadroDiploma = new Producto();
            cuadroDiploma.setNombre("Marco para Diplomas");
            cuadroDiploma.setDescripcion("Especializado en enmarcación de diplomas y certificados importantes con protección UV.");
            cuadroDiploma.setPrecio(15000.0);
            cuadroDiploma.setStock(40);
            cuadroDiploma.setImagenUrl("/assets/marco.diplomaa.png");
            cuadroDiploma.setCategoria(cuadrosSaved);

            Producto cuadroVintage = new Producto();
            cuadroVintage.setNombre("Marco Vintage Antiguo");
            cuadroVintage.setDescripcion("Estilo vintage con detalles ornamentales, perfecto para fotografías familiares clásicas.");
            cuadroVintage.setPrecio(28000.0);
            cuadroVintage.setStock(15);
            cuadroVintage.setImagenUrl("/assets/marcoantigo.png");
            cuadroVintage.setCategoria(cuadrosSaved);

            // Guardamos todos los productos
            productoRepository.saveAll(Arrays.asList(
                p1, p2, p3, p4,
                grecaZo, grecaCorazon, grecaOro, grecaPlata,
                rusticaAzul, naturalAlerce, nativaJ16, fingerP12,
                cuadroDorado, cuadroMinimalista, cuadroRustico, cuadroDiploma, cuadroVintage
            ));

            System.out.println(">>> ¡DATOS CARGADOS EXITOSAMENTE! <<<");
        } else {
            System.out.println(">>> La base de datos ya tiene datos. No se requiere carga inicial. <<<");
        }
    }
}