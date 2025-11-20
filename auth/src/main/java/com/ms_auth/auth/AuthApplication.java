package com.ms_auth.auth;

import com.ms_auth.auth.model.Usuario;
import com.ms_auth.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    // Este método se ejecuta automáticamente al iniciar el servidor
    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Buscamos si ya existe el admin
            Optional<Usuario> admin = usuarioRepository.findByEmail("admin@legacyframes.cl");

            if (admin.isEmpty()) {
                // Si no existe, lo creamos
                Usuario newAdmin = new Usuario();
                newAdmin.setNombre("Admin");
                newAdmin.setApellido("Sistema");
                newAdmin.setEmail("admin@legacyframes.cl");
                newAdmin.setPassword(passwordEncoder.encode("admin123!")); // Contraseña por defecto
                newAdmin.setRol("ADMIN");
                newAdmin.setTelefono("00000000");
                newAdmin.setDireccion("Oficina Central");
                
                usuarioRepository.save(newAdmin);
                System.out.println(">>> USUARIO ADMIN CREADO AUTOMÁTICAMENTE <<<");
            }
        };
    }
}