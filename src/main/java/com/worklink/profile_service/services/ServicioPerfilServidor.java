package com.worklink.profile_service.services;

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

}
