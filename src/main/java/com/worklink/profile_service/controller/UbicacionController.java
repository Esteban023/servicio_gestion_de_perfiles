package com.worklink.profile_service.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

import com.worklink.profile_service.DTOS.UbicacionDTO;
import com.worklink.profile_service.Mappers.UbicacionMapper;
import com.worklink.profile_service.model.Ubicacion;
import com.worklink.profile_service.services.ServicioUbicacion;

@RestController
@RequestMapping("/api/ubicacion")
public class UbicacionController {

    @Autowired
    private ServicioUbicacion servicioUbicacion;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(UbicacionController.class);

    /**
     * Obtiene todas las ubicaciones
     */
    @GetMapping
    public ResponseEntity<List<Ubicacion>> obtenerTodasLasUbicaciones() {
        try {
            List<Ubicacion> ubicaciones = servicioUbicacion.obtenerTodasLasUbicaciones();
            return ResponseEntity.ok(ubicaciones);
        } catch (Exception e) {
            logger.error("Error al obtener ubicaciones", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtiene una ubicación por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UbicacionDTO> obtenerUbicacionPorId(@PathVariable Long id) {
        try {
            Optional<Ubicacion> ubicacionOpt = servicioUbicacion.obtenerUbicacionPorId(id);
            
            if (ubicacionOpt.isPresent()) {
                UbicacionDTO ubicacionDTO = UbicacionMapper.toDto(ubicacionOpt.get());
                return ResponseEntity.ok(ubicacionDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logger.error("Error al obtener ubicación con ID: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtiene la ubicación de un cliente
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<UbicacionDTO> obtenerUbicacionPorClienteId(@PathVariable Long clienteId) {
        try {
            Optional<Ubicacion> ubicacionOpt = servicioUbicacion.obtenerUbicacionPorClienteId(clienteId);
            
            if (ubicacionOpt.isPresent()) {
                UbicacionDTO ubicacionDTO = UbicacionMapper.toDto(ubicacionOpt.get());
                return ResponseEntity.ok(ubicacionDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            logger.error("Error al obtener ubicación del cliente: " + clienteId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Crea una nueva ubicación
     */
    @PostMapping
    public ResponseEntity<UbicacionDTO> crearUbicacion(@RequestBody UbicacionDTO ubicacionDTO) {
        try {
            logger.info("=== Ubicación recibida ===");
            logger.info("Latitud: {}", ubicacionDTO.getLatitud());
            logger.info("Longitud: {}", ubicacionDTO.getLongitud());
            logger.info("Dirección: {}", ubicacionDTO.getDireccion());
            logger.info("Ciudad: {}", ubicacionDTO.getCiudad());
            logger.info("Departamento: {}", ubicacionDTO.getDepartamento());

            // Validar que los campos obligatorios no sean nulos
            if (ubicacionDTO.getLatitud() == null || ubicacionDTO.getLongitud() == null) {
                logger.error("Latitud y Longitud son obligatorias");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // Convertir DTO a entidad
            Ubicacion ubicacion = UbicacionMapper.toEntity(ubicacionDTO);
            
            // Guardar en BD
            Ubicacion ubicacionGuardada = servicioUbicacion.guardarUbicacion(ubicacion);
            
            logger.info("Ubicación guardada con ID: {}", ubicacionGuardada.getId());
            
            // Convertir la entidad guardada a DTO y retornar
            UbicacionDTO ubicacionGuardadaDTO = UbicacionMapper.toDto(ubicacionGuardada);
            return ResponseEntity.status(HttpStatus.CREATED).body(ubicacionGuardadaDTO);
            
        } catch (IllegalArgumentException e) {
            logger.error("Error de validación: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Error al crear ubicación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Actualiza una ubicación existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<UbicacionDTO> actualizarUbicacion(@PathVariable Long id, 
                                                             @RequestBody UbicacionDTO ubicacionDTO) {
        try {
            logger.info("Actualizando ubicación con ID: {}", id);
            
            Ubicacion ubicacionActualizada = UbicacionMapper.toEntity(ubicacionDTO);
            Ubicacion ubicacionGuardada = servicioUbicacion.actualizarUbicacion(id, ubicacionActualizada);
            
            UbicacionDTO ubicacionGuardadaDTO = UbicacionMapper.toDto(ubicacionGuardada);
            return ResponseEntity.ok(ubicacionGuardadaDTO);
            
        } catch (IllegalArgumentException e) {
            logger.error("Ubicación no encontrada: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error al actualizar ubicación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Elimina una ubicación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUbicacion(@PathVariable Long id) {
        try {
            logger.info("Eliminando ubicación con ID: {}", id);
            servicioUbicacion.eliminarUbicacion(id);
            return ResponseEntity.noContent().build();
            
        } catch (IllegalArgumentException e) {
            logger.error("Ubicación no encontrada: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Error al eliminar ubicación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
