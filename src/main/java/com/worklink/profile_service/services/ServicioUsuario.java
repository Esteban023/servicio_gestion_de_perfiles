package com.worklink.profile_service.services;

import java.time.LocalDateTime;

import com.worklink.profile_service.DTOS.UsuarioDTO;
import com.worklink.profile_service.model.Usuario;

public class ServicioUsuario {

    public Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setTelefono(dto.getTelefono());
        usuario.setFotoPerfilUrl(dto.getFotoPerfilUrl());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setFechaRegistro(LocalDateTime.now()); // Campo adicional
        return usuario;
    }
}
