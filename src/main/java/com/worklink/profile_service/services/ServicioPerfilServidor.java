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

}
