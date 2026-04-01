package com.worklink.profile_service.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.worklink.profile_service.model.PerfilProveedor;
import org.springframework.beans.factory.annotation.Autowired;
import com.worklink.profile_service.repository.RepositorioPerfilServidor;

@Service
public class ServicioPerfilServidor {

    @Autowired RepositorioPerfilServidor repPerfilServidor;

    public PerfilProveedor guardarPerfilServidor(PerfilProveedor perfil) {
        return (PerfilProveedor) repPerfilServidor.save(perfil);
    }

    public Optional<PerfilProveedor> obtenerPerfilServidor(String email) {
        return repPerfilServidor.findById(email);
    }
    
    public Optional<PerfilProveedor> actualizarPerfilServidor(String email, PerfilProveedor perfilActualizado) {
        Optional<PerfilProveedor> perfilOpt = repPerfilServidor.findById(email);
        
        if (perfilOpt.isEmpty()) {
            return Optional.empty();
        }
        
        PerfilProveedor perfilExistente = perfilOpt.get();
        perfilExistente.setBiografia(perfilActualizado.getBiografia());
        perfilExistente.setHorarioDisponibilidad(perfilActualizado.getHorarioDisponibilidad());
        //perfilExistente.setCategoriaServicio(perfilActualizado.getCategoriaServicio());
        
        PerfilProveedor perfilGuardado = repPerfilServidor.save(perfilExistente);
        return Optional.of(perfilGuardado);
    }

    private void validarPortafolio() {
         

    }

    public void eliminarPerfilServidor(String email) {
        repPerfilServidor.deleteById(email);
    }
}
