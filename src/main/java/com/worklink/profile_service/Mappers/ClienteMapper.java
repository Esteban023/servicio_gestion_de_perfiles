package com.worklink.profile_service.Mappers;

import com.worklink.profile_service.DTOS.ClienteDTO;
import com.worklink.profile_service.model.Cliente;

public class ClienteMapper {

    public static ClienteDTO toDto(Cliente perfilCliente) {
        if (perfilCliente == null) {
            return null;
        }
        
        ClienteDTO dto = new ClienteDTO();
        dto.setActivo(perfilCliente.isActivo());
        dto.setVerificado(perfilCliente.isVerificado());
        dto.setRatingPromedio(perfilCliente.getRatingPromedio());
        dto.setOcupacion(perfilCliente.getOcupacion());
        
        return dto;
    }
}
