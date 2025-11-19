package com.ms_auth.auth.controller;

import com.ms_auth.auth.dto.AuthUserDto;
import com.ms_auth.auth.dto.LoginDto;
import com.ms_auth.auth.dto.TokenDto;
import com.ms_auth.auth.model.Usuario;
import com.ms_auth.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
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
    
    // Perfil (Requiere enviar el token para saber quién es)
    // Para esto normalmente se usa un @RequestHeader("Authorization")
}