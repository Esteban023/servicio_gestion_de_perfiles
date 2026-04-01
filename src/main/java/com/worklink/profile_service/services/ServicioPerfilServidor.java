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
        Optional<PerfilProveedor> perfilExistenteOpt = repPerfilServidor.findById(email);

        if (perfilExistenteOpt.isPresent()) {
            PerfilProveedor perfilExistente = perfilExistenteOpt.get();
            
            perfilExistente.setNombre(
                perfilActualizado.getNombre()
            );
            perfilExistente.setApellido(
                perfilActualizado.getApellido()
            );
            perfilExistente.setPhone(
                perfilActualizado.getPhone()
            );
            perfilExistente.setFotoPerfilUrl(
                perfilActualizado.getFotoPerfilUrl()
            );
            
            return Optional.of(repPerfilServidor.save(perfilExistente));
        } else {
            return perfilExistenteOpt;
        }
    }

    private void validarPortafolio() {
         

    }

    public void eliminarPerfilServidor(String email) {
        repPerfilServidor.deleteById(email);
    }
}
