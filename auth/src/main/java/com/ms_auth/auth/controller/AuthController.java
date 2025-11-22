package com.ms_auth.auth.controller;

import com.ms_auth.auth.dto.AuthUserDto;
import com.ms_auth.auth.dto.UserProfileDto;
import com.ms_auth.auth.dto.LoginDto;
import com.ms_auth.auth.dto.TokenDto;
import com.ms_auth.auth.model.Usuario;
import com.ms_auth.auth.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.ms_auth.auth.dto.UpdateProfileDto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Usuario> create(@RequestBody AuthUserDto dto) {
        return ResponseEntity.ok(authService.save(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token) {
        return ResponseEntity.ok(authService.validate(token));
    }

    @GetMapping("/perfil")
    public ResponseEntity<UserProfileDto> verPerfil(@RequestParam String email) {
        // Llamamos al servicio
        UserProfileDto perfil = authService.obtenerPerfil(email);
        return ResponseEntity.ok(perfil);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserProfileDto>> listarUsuarios() {
        return ResponseEntity.ok(authService.listarTodos());
    }

    @PutMapping("/update")
    public ResponseEntity<UserProfileDto> actualizar(@RequestParam String email, @RequestBody AuthUserDto dto) {
        return ResponseEntity.ok(authService.actualizarUsuario(email, dto));
    }

    // PUT para actualizar MI propio perfil
    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> actualizarMiPerfil(@RequestBody UpdateProfileDto dto) {
        
        // 1. Obtenemos el usuario logueado automáticamente desde el Token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailDelUsuario = auth.getName(); // Spring Security guardó el email aquí

        // 2. Llamamos al servicio
        UserProfileDto perfilActualizado = authService.actualizarPerfilPropio(emailDelUsuario, dto);
        
        return ResponseEntity.ok(perfilActualizado);
    }
    
}