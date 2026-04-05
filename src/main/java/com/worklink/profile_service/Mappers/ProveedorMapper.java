package com.worklink.profile_service.Mappers;

import com.worklink.profile_service.DTOS.ProveedorDTO;
import com.worklink.profile_service.model.Proveedor;

public class ProveedorMapper {

    public static ProveedorDTO toDto(Proveedor proveedor) {
        if (proveedor == null) {
            return null;
        }
        
        ProveedorDTO dto = new ProveedorDTO();
        dto.setVerificado(proveedor.isVerificado());
        dto.setBiografia(proveedor.getBiografia());
        dto.setRatingPromedio(proveedor.getRatingPromedio());
        dto.setHorarioDisponibilidad(proveedor.getHorarioDisponibilidad());
        
        return dto;
    }

    public static Proveedor toEntity(ProveedorDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Proveedor perfilProveedor = new Proveedor();
        perfilProveedor.setVerificado(dto.getVerificado());
        perfilProveedor.setBiografia(dto.getBiografia());
        perfilProveedor.setRatingPromedio(dto.getRatingPromedio());
        perfilProveedor.setHorarioDisponibilidad(dto.getHorarioDisponibilidad());
        
        return perfilProveedor;
    }

    
}
