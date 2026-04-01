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
        Optional<PerfilCliente> perfilExistenteOpt = repositorioPerfilCliente.findById(email);

        if (perfilExistenteOpt.isPresent()) {
            PerfilCliente perfilExistente = perfilExistenteOpt.get();
            
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
            perfilExistente.setDescripcion(
                perfilActualizado.getDescripcion()
            );
            
            return repositorioPerfilCliente.save(perfilExistente);
        } else {
            throw new RuntimeException("Perfil no encontrado");
        }
    }

    public void eliminarPerfilCliente(String email) {
        repositorioPerfilCliente.deleteById(email);
    }




}
