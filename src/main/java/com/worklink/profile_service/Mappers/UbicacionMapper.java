package com.worklink.profile_service.Mappers;

import com.worklink.profile_service.DTOS.UbicacionDTO;
import com.worklink.profile_service.model.Ubicacion;

public class UbicacionMapper {

    public static UbicacionDTO toDto(Ubicacion ubicacion) {
        if (ubicacion == null) {
            return null;
        }
        
        UbicacionDTO dto = new UbicacionDTO();
        dto.setId(ubicacion.getId());
        dto.setLatitud(ubicacion.getLatitud());
        dto.setLongitud(ubicacion.getLongitud());
        dto.setDireccion(ubicacion.getDireccion());
        dto.setCiudad(ubicacion.getCiudad());
        dto.setDepartamento(ubicacion.getDepartamento());
        
        return dto;
    }

    public static Ubicacion toEntity(UbicacionDTO ubicacionDTO) {
        if (ubicacionDTO == null) {
            return null;
        }
        
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setLatitud(ubicacionDTO.getLatitud());
        ubicacion.setLongitud(ubicacionDTO.getLongitud());
        ubicacion.setDireccion(ubicacionDTO.getDireccion());
        ubicacion.setCiudad(ubicacionDTO.getCiudad());
        ubicacion.setDepartamento(ubicacionDTO.getDepartamento());
        
        return ubicacion;
    }
}
