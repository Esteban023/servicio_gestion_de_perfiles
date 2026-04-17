package com.worklink.profile_service.Mappers;

import com.worklink.profile_service.DTOS.ClienteDTO;
import com.worklink.profile_service.model.Cliente;

public class ClienteMapper {

    public static ClienteDTO toDto(Cliente perfilCliente) {
        if (perfilCliente == null) {
            return null;
        }
        
        ClienteDTO dto = new ClienteDTO();
        if (perfilCliente.getUsuario() != null){
            dto.setUsuario(UsuarioMapper.toDto(perfilCliente.getUsuario()));
        }
        dto.setActivo(perfilCliente.isActivo());
        dto.setVerificado(perfilCliente.isVerificado());
        dto.setRatingPromedio(perfilCliente.getRatingPromedio());
        dto.setOcupacion(perfilCliente.getOcupacion());
        
        return dto;
    }

    public static Cliente toEntity(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            return null;
        }
        
        Cliente perfilCliente = new Cliente();
        
        perfilCliente.setActivo(
            clienteDTO.isActivo()
        );
        perfilCliente.setVerificado(
            clienteDTO.isVerificado()
        );
        perfilCliente.setRatingPromedio(
            clienteDTO.getRatingPromedio()
        );
        perfilCliente.setOcupacion(
            clienteDTO.getOcupacion()
        );
        
        return perfilCliente;
    }
}
