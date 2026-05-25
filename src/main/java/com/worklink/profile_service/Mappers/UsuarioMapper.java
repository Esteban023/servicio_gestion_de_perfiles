package com.worklink.profile_service.Mappers;

import com.worklink.profile_service.DTOS.UsuarioDTO;
import com.worklink.profile_service.model.Usuario;

public class UsuarioMapper {
    
    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setTelefono(dto.getTelefono());
        usuario.setFotoPerfilUrl(dto.getFotoPerfilUrl());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        // fechaRegistro se asigna después de llamar a toEntity
        
        return usuario;
    }
    
    public static UsuarioDTO toDto(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        
        UsuarioDTO dto = new UsuarioDTO();
        dto.setEmail(usuario.getEmail());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setTelefono(usuario.getTelefono());
        dto.setFotoPerfilUrl(usuario.getFotoPerfilUrl());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        
        return dto;
    }
}