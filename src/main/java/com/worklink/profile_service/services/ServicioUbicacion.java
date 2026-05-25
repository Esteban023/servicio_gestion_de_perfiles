package com.worklink.profile_service.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.worklink.profile_service.model.Ubicacion;
import com.worklink.profile_service.repository.RepositorioUbicacion;

@Service
public class ServicioUbicacion {

    @Autowired
    private RepositorioUbicacion repositorioUbicacion;

    /**
     * Obtiene todas las ubicaciones
     */
    public List<Ubicacion> obtenerTodasLasUbicaciones() {
        return repositorioUbicacion.findAll();
    }

    /**
     * Obtiene una ubicación por ID
     */
    public Optional<Ubicacion> obtenerUbicacionPorId(Long id) {
        return repositorioUbicacion.findById(id);
    }

    /**
     * Obtiene la ubicación asociada a un cliente
     */
    public Optional<Ubicacion> obtenerUbicacionPorClienteId(Long clienteId) {
        return repositorioUbicacion.findUbicationByClienteId(clienteId);
    }

    /**
     * Guarda una nueva ubicación
     */
    public Ubicacion guardarUbicacion(Ubicacion ubicacion) {
        if (ubicacion == null) {
            throw new IllegalArgumentException("La ubicación no puede ser nula");
        }
        
        // Validaciones básicas
        if (ubicacion.getLatitud() == null || ubicacion.getLongitud() == null) {
            throw new IllegalArgumentException("Latitud y Longitud son obligatorias");
        }
        
        return repositorioUbicacion.save(ubicacion);
    }

    /**
     * Actualiza una ubicación existente
     */
    public Ubicacion actualizarUbicacion(Long id, Ubicacion ubicacionActualizada) {
        Optional<Ubicacion> ubicacionOpt = repositorioUbicacion.findById(id);
        
        if (ubicacionOpt.isEmpty()) {
            throw new IllegalArgumentException("Ubicación no encontrada con ID: " + id);
        }
        
        Ubicacion ubicacionExistente = ubicacionOpt.get();
        
        if (ubicacionActualizada.getLatitud() != null) {
            ubicacionExistente.setLatitud(ubicacionActualizada.getLatitud());
        }
        if (ubicacionActualizada.getLongitud() != null) {
            ubicacionExistente.setLongitud(ubicacionActualizada.getLongitud());
        }
        if (ubicacionActualizada.getDireccion() != null) {
            ubicacionExistente.setDireccion(ubicacionActualizada.getDireccion());
        }
        if (ubicacionActualizada.getCiudad() != null) {
            ubicacionExistente.setCiudad(ubicacionActualizada.getCiudad());
        }
        if (ubicacionActualizada.getDepartamento() != null) {
            ubicacionExistente.setDepartamento(ubicacionActualizada.getDepartamento());
        }
        
        return repositorioUbicacion.save(ubicacionExistente);
    }

    /**
     * Elimina una ubicación por ID
     */
    public void eliminarUbicacion(Long id) {
        if (!repositorioUbicacion.existsById(id)) {
            throw new IllegalArgumentException("Ubicación no encontrada con ID: " + id);
        }
        repositorioUbicacion.deleteById(id);
    }
}
