package com.ms_auth.auth.service;

import com.ms_auth.auth.dto.AuthUserDto;
import com.ms_auth.auth.dto.LoginDto;
import com.ms_auth.auth.dto.TokenDto;
import com.ms_auth.auth.model.Usuario;
import com.ms_auth.auth.repository.UsuarioRepository;
import com.ms_auth.auth.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public Usuario save(AuthUserDto dto) {
        // 1. Validar si el email ya existe
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        // 2. Validar que las contraseñas coincidan (Lo que pediste)
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }

        // 3. Crear el usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        
        // Campos opcionales (se guardan tal cual, si vienen null quedan null en BD)
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());

        // 4. Encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        // Rol por defecto
        usuario.setRol("USER"); 

        return usuarioRepository.save(usuario);
    }

    // ... (Los métodos login y validate se mantienen igual que antes) ...
    public TokenDto login(LoginDto dto) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(dto.getEmail());
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        Usuario usuario = userOpt.get();
        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        return new TokenDto(jwtProvider.createToken(usuario));
    }

    public TokenDto validate(String token) {
        if (!jwtProvider.validateToken(token)) {
            throw new RuntimeException("Token inválido");
        }
        return new TokenDto(token); 
    }
}