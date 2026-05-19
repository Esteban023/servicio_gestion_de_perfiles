package com.worklink.profile_service.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.worklink.profile_service.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import com.worklink.profile_service.repository.RepositorioPerfilCliente;

@Service
public class ServicioPerfilCliente {

    @Autowired
    private RepositorioPerfilCliente repositorioPerfilCliente;

    public List<Cliente> obtenerTodosLosPerfilesClientes() {
        return repositorioPerfilCliente.findAll();
    }

    public Cliente guardarPerfilCliente(Cliente perfil) {
        return repositorioPerfilCliente.save(perfil);
    }

    public Optional<Cliente> obtenerPerfilCliente(String email) {
        return repositorioPerfilCliente.findByUsuarioEmail(email);
    }

    public Cliente actualizarPerfilCliente(String email, Cliente perfilActualizado) {
        Optional<Cliente> perfilOpt = repositorioPerfilCliente.findByUsuarioEmail(email);
        
        if (perfilOpt.isEmpty()) {
            return null;
        }
        
        Cliente perfilExistente = perfilOpt.get();
        perfilExistente.setUsuario(
            perfilActualizado.getUsuario()
        );
        perfilExistente.setActivo(
            perfilActualizado.isActivo()
        );
        perfilExistente.setVerificado(
            perfilActualizado.isVerificado()
        );
        perfilExistente.setRatingPromedio(
            perfilActualizado.getRatingPromedio()
        );
        perfilExistente.setOcupacion(
            perfilActualizado.getOcupacion()
        );
        
        return repositorioPerfilCliente.save(perfilExistente);
    }

    public void eliminarPerfilCliente(String email) {
        repositorioPerfilCliente.deleteByUsuarioEmail(email);
    }

    public Optional<Cliente> obtenerPerfilClientePorId(Long id) {
        return repositorioPerfilCliente.findById(id);
    }

}
