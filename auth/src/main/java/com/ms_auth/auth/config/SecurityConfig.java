package com.ms_auth.auth.config;

import com.ms_auth.auth.security.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy; // Importante para la sesión STATELESS

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desactivamos CSRF porque usamos Tokens
            .csrf(csrf -> csrf.disable())
            
            // Configuración de rutas
            .authorizeHttpRequests(auth -> auth
                // 1. Rutas públicas de Autenticación
                .requestMatchers("/auth/login", "/auth/register", "/auth/validate").permitAll()
                
                // 2. --- AQUÍ ESTÁ LA SOLUCIÓN PARA SWAGGER (Error 403) ---
                // Permitimos el acceso a la documentación y la interfaz visual
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // ---------------------------------------------------------

                // 3. Rutas SOLO para ADMIN
                .requestMatchers("/auth/list").hasAuthority("ADMIN") 
                
                // 4. Cualquier otra ruta requiere Token
                .anyRequest().authenticated()
            )
            // Configuramos la sesión como "Sin Estado" (Stateless)
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // Agregamos el filtro de JWT antes del filtro estándar
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Encriptación segura
    }
}