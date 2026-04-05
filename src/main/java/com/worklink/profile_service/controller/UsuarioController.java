package com.worklink.profile_service.controller;

import java.util.Optional;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.worklink.profile_service.model.Usuario;
import com.worklink.profile_service.DTOS.UsuarioDTO;
import com.worklink.profile_service.Mappers.UsuarioMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.worklink.profile_service.repository.RepositorioUsuario;

import ch.qos.logback.classic.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private RepositorioUsuario repoUsuario;

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody UsuarioDTO usuario) {
        Logger logger = (Logger) LoggerFactory.getLogger(getClass());
    
        // Log completo del objeto recibido
        logger.info("=== Usuario recibido ===");
        logger.info("Email: {}", usuario.getEmail());
        logger.info("Nombre: {}", usuario.getNombre());
        logger.info("Apellido: {}", usuario.getApellido());
        logger.info("Teléfono: {}", usuario.getTelefono());
        logger.info("Foto URL: {}", usuario.getFotoPerfilUrl());
        logger.info("Fecha Nac: {}", usuario.getFechaNacimiento());
        
        String email = usuario.getEmail();
        
        if (email == null || email.trim().isEmpty()) {
            logger.error("Email es null o vacío!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<Usuario> usuarioOpt = repoUsuario.findById(email);
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        try {
            Usuario nuevoUsuario = UsuarioMapper.toEntity(usuario);
            nuevoUsuario.setFechaRegistro(LocalDateTime.now());
            Usuario savedUsuario = repoUsuario.save(nuevoUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable String email) {
        try {
            Optional<Usuario> usuarioOpt = repoUsuario.findById(email.toLowerCase());
            if (usuarioOpt.isPresent()) {
                UsuarioDTO usuarioDTO = UsuarioMapper.toDto(usuarioOpt.get());
                return ResponseEntity.ok(usuarioDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    


}