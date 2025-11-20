package com.ms_auth.auth.service;

import com.ms_auth.auth.dto.AuthUserDto;
import com.ms_auth.auth.dto.LoginDto;
import com.ms_auth.auth.dto.TokenDto;
import com.ms_auth.auth.dto.UpdateProfileDto;
import com.ms_auth.auth.dto.UserProfileDto;
import com.ms_auth.auth.model.Usuario;
import com.ms_auth.auth.repository.UsuarioRepository;
import com.ms_auth.auth.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

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

    // Método para obtener el perfil por email
    public UserProfileDto obtenerPerfil(String email) {
        // 1. Buscamos al usuario en la BD
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Pasamos los datos al DTO (para no enviar la password)
        UserProfileDto perfil = new UserProfileDto();
        perfil.setNombre(usuario.getNombre());
        perfil.setApellido(usuario.getApellido());
        perfil.setEmail(usuario.getEmail());
        perfil.setTelefono(usuario.getTelefono());
        perfil.setDireccion(usuario.getDireccion());

        return perfil;
    }

    // Listar todos (Devolvemos UserProfileDto para no mostrar passwords)
    public List<UserProfileDto> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        
        // Convertimos la lista de Entidades a lista de DTOs
        return usuarios.stream().map(usuario -> {
            UserProfileDto dto = new UserProfileDto();
            dto.setNombre(usuario.getNombre());
            dto.setApellido(usuario.getApellido());
            dto.setEmail(usuario.getEmail());
            dto.setTelefono(usuario.getTelefono());
            dto.setDireccion(usuario.getDireccion());
            return dto;
        }).collect(Collectors.toList());
    }


    // Modificar usuario
    public UserProfileDto actualizarUsuario(String email, AuthUserDto datosNuevos) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizamos los campos (solo si vienen con datos)
        usuario.setNombre(datosNuevos.getNombre());
        usuario.setApellido(datosNuevos.getApellido());
        usuario.setTelefono(datosNuevos.getTelefono());
        usuario.setDireccion(datosNuevos.getDireccion());

        // Si quieres permitir cambiar contraseña:
        if (datosNuevos.getPassword() != null && !datosNuevos.getPassword().isEmpty()) {
             usuario.setPassword(passwordEncoder.encode(datosNuevos.getPassword()));
        }

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Convertimos a DTO para devolverlo
        UserProfileDto perfil = new UserProfileDto();
        perfil.setNombre(usuarioGuardado.getNombre());
        perfil.setApellido(usuarioGuardado.getApellido());
        perfil.setEmail(usuarioGuardado.getEmail());
        perfil.setTelefono(usuarioGuardado.getTelefono());
        perfil.setDireccion(usuarioGuardado.getDireccion());
        
        return perfil;
    }


    public UserProfileDto actualizarPerfilPropio(String emailDelToken, UpdateProfileDto dto) {
        // 1. Buscamos al usuario que está haciendo la petición
        Usuario usuario = usuarioRepository.findByEmail(emailDelToken)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Actualizamos SOLO los datos permitidos (El email NO se toca)
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setTelefono(dto.getTelefono());
        usuario.setDireccion(dto.getDireccion());

        // 3. Lógica de Contraseña: Solo si el usuario escribió una nueva
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            // Verificamos confirmación
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                throw new RuntimeException("Las contraseñas nuevas no coinciden");
            }
            // Encriptamos y guardamos
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        // 4. Guardamos
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 5. Devolvemos el perfil actualizado (sin pass)
        UserProfileDto perfil = new UserProfileDto();
        perfil.setNombre(usuarioGuardado.getNombre());
        perfil.setApellido(usuarioGuardado.getApellido());
        perfil.setEmail(usuarioGuardado.getEmail()); // Devolvemos el mismo email
        perfil.setTelefono(usuarioGuardado.getTelefono());
        perfil.setDireccion(usuarioGuardado.getDireccion());
        
        return perfil;
    }

    

}