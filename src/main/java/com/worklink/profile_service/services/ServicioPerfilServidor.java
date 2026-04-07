package com.worklink.profile_service.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.worklink.profile_service.model.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import com.worklink.profile_service.repository.RepositorioPerfilServidor;

@Service
public class ServicioPerfilServidor {

    @Autowired RepositorioPerfilServidor repPerfilServidor;

    public Proveedor guardarPerfilServidor(Proveedor perfil) {
        return (Proveedor) repPerfilServidor.save(perfil);
    }

    public Optional<Proveedor> obtenerPerfilServidorPorEmail(String email) {
        return repPerfilServidor.findByUsuarioEmail(email);
    }

    public Proveedor actualizarPerfilServidor(String email, Proveedor perfilActualizado) {
        Optional<Proveedor> perfilOpt = repPerfilServidor.findByUsuarioEmail(email);
        
        if (perfilOpt.isEmpty()) {
            return null;
        }
        
        Proveedor perfilExistente = perfilOpt.get();
        perfilExistente.setUsuario(perfilActualizado.getUsuario());
        perfilExistente.setBiografia(perfilActualizado.getBiografia());
        perfilExistente.setActivo(perfilActualizado.isActivo());
        perfilExistente.setVerificado(perfilActualizado.isVerificado());
        perfilExistente.setHorarioDisponibilidad(perfilActualizado.getHorarioDisponibilidad());
        perfilExistente.setRatingPromedio(perfilActualizado.getRatingPromedio());
        perfilExistente.setCedulaUrl(perfilActualizado.getCedulaUrl());
        perfilExistente.setCertificadoSaludUrl(perfilActualizado.getCertificadoSaludUrl());
        perfilExistente.setCertificadoAntecedentesUrl(perfilActualizado.getCertificadoAntecedentesUrl());
        perfilExistente.setCertificadoInhabilidadesUrl(perfilActualizado.getCertificadoInhabilidadesUrl());
        
        Proveedor perfilGuardado = repPerfilServidor.save(perfilExistente);
        return perfilGuardado;
    }

}
