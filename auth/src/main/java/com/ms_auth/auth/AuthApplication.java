package com.ms_auth.auth;

import com.ms_auth.auth.model.Rol;
import com.ms_auth.auth.model.Usuario;
import com.ms_auth.auth.repository.RolRepository;
import com.ms_auth.auth.repository.UsuarioRepository;
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

    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Crear roles si no existen
            if (rolRepository.count() == 0) {
                rolRepository.save(new Rol(null, "ROLE_USER"));
                rolRepository.save(new Rol(null, "ROLE_ADMIN"));
                System.out.println(">>> ROLES CREADOS <<<");
            }

            // 2. Crear admin si no existe
            Optional<Usuario> admin = usuarioRepository.findByEmail("admin@legacyframes.cl");

            if (admin.isEmpty()) {
                Usuario newAdmin = new Usuario();
                newAdmin.setNombre("Admin");
                newAdmin.setApellido("Sistema");
                newAdmin.setEmail("admin@legacyframes.cl");
                newAdmin.setPassword(passwordEncoder.encode("admin123!"));
                newAdmin.setTelefono("00000000");
                newAdmin.setDireccion("Oficina Central");
                
                // Asignar rol ADMIN desde BD
                Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN").orElse(null);
                newAdmin.setRol(rolAdmin);
                
                usuarioRepository.save(newAdmin);
                System.out.println(">>> USUARIO ADMIN CREADO AUTOMÁTICAMENTE <<<");
            }
        };
    }
}