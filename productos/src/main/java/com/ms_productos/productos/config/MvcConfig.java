package com.ms_productos.productos.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths; // <--- NO OLVIDES ESTE IMPORT

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173", "http://localhost:3000", "http://localhost:5174")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // --- LÓGICA A PRUEBA DE FALLOS ---
        // 1. Definimos la ruta base
        String uploadPath = "uploads/";
        
        // 2. Preguntamos dónde se está ejecutando el servidor
        String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();

        // 3. Si NO termina en "productos", significa que estamos en la carpeta de atrás (APIS_LF)
        // Por lo tanto, le agregamos el prefijo para entrar a la carpeta correcta.
        if (!currentDir.endsWith("productos")) {
            uploadPath = "productos/uploads/";
        }

        // 4. Configuramos el acceso público a las imágenes
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadPath);
                
        // Debug para que veas en la consola dónde está buscando (opcional)
        System.out.println(">>> MvcConfig: Sirviendo imágenes desde: " + uploadPath);
    }
}