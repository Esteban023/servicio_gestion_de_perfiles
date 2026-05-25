package com.worklink.profile_service.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.worklink.profile_service.DTOS.CuentaBancariaRequest;
import com.worklink.profile_service.model.Proveedor;
import com.worklink.profile_service.model.Ubicacion;
import com.worklink.profile_service.repository.RepositorioPerfilServidor;
import com.worklink.profile_service.repository.RepositorioUbicacion;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ServicioPerfilServidor {

    @Autowired
    RepositorioPerfilServidor repPerfilServidor;

    @Autowired
    RepositorioUbicacion repositorioUbicacion;

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

    public Optional<Proveedor> obtenerPerfilServidorPorId(Long id) {
        return repPerfilServidor.findById(id);
    }

    public List<Proveedor> obtenerTodos() {
        return repPerfilServidor.findAll();
    }

    // --- Cuenta bancaria colombiana ---
    public void guardarCuentaBancaria(Long proveedorID, CuentaBancariaRequest request) {
        Optional<Proveedor> proveedorOpt = repPerfilServidor.findById(proveedorID);

        if (proveedorOpt.isEmpty()) {
            throw new RuntimeException("Proveedor no encontrado con ID: " + proveedorID);
        }

        Proveedor proveedor = proveedorOpt.get();
        proveedor.setTitular(request.getTitular());
        proveedor.setNumeroCuenta(request.getNumeroCuenta());
        proveedor.setTipoCuenta(request.getTipoCuenta());
        proveedor.setBanco(request.getBanco());
        proveedor.setDocumento(request.getDocumento());
        proveedor.setCuentaVinculada(true);
        proveedor.setCuentaVinculadaAt(LocalDateTime.now());

        repPerfilServidor.save(proveedor);
    }

    public Proveedor obtenerCuentaBancaria(Long proveedorID) {
        Optional<Proveedor> proveedorOpt = repPerfilServidor.findById(proveedorID);

        if (proveedorOpt.isEmpty()) {
            throw new RuntimeException("Proveedor no encontrado con ID: " + proveedorID);
        }

        return proveedorOpt.get();
    }

    public List<Proveedor> obtenerProveedoresPorCercania(Long clienteId, Double radioKm) {
        Optional<Ubicacion> ubicacionOpt = repositorioUbicacion.findUbicationByClienteId(clienteId);

        if (!ubicacionOpt.isPresent()) {
            return List.of();
        }

        Ubicacion ubicacion = ubicacionOpt.get();
        Double radioGrados = radioKm / 111.195;

        return repPerfilServidor.findProveedoresCercanos(
            ubicacion.getLatitud(),
            ubicacion.getLongitud(),
            radioGrados
        );

    }
}