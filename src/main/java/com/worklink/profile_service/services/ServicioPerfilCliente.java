package com.worklink.profile_service.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.worklink.profile_service.model.PerfilCliente;
import org.springframework.beans.factory.annotation.Autowired;
import com.worklink.profile_service.repository.RepositorioPerfilCliente;

@Service
public class ServicioPerfilCliente {

    @Autowired
    private RepositorioPerfilCliente repositorioPerfilCliente;

    public List<PerfilCliente> obtenerTodosLosPerfilesClientes() {
        return repositorioPerfilCliente.findAll();
    }

    public PerfilCliente guardarPerfilCliente(PerfilCliente perfil) {
        return repositorioPerfilCliente.save(perfil);
    }

    public Optional<PerfilCliente> obtenerPerfilCliente(String email) {
        return repositorioPerfilCliente.findById(email);
    }

    public PerfilCliente actualizarPerfilCliente(String email, PerfilCliente perfilActualizado) {
        Optional<PerfilCliente> perfilOpt = repositorioPerfilCliente.findById(email);
        
        if (perfilOpt.isEmpty()) {
            return null;
        }
        
        PerfilCliente perfilExistente = perfilOpt.get();
        perfilExistente.setActivo(perfilActualizado.isActivo());
        perfilExistente.setVerificado(perfilActualizado.isVerificado());
        perfilExistente.setRatingPromedio(perfilActualizado.getRatingPromedio());
        
        PerfilCliente perfilGuardado = repositorioPerfilCliente.save(perfilExistente);
        return perfilGuardado;
    }

    public void eliminarPerfilCliente(String email) {
        repositorioPerfilCliente.deleteById(email);
    }




}
