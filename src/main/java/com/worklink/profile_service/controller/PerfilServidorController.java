package com.worklink.profile_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.worklink.profile_service.model.Portafolio;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import com.worklink.profile_service.model.PerfilProveedor;
import org.springframework.beans.factory.annotation.Autowired;
import com.worklink.profile_service.security.CustomUserDetails;
import com.worklink.profile_service.services.ServicioPerfilServidor;

@RestController
@RequestMapping("/api/perfil-servidor")
public class PerfilServidorController {

    private static final int MAX_PORTAFOLIOS = 5;

    @Autowired
    private ServicioPerfilServidor servicioPerfilServidor;

    @PostMapping
    public ResponseEntity<PerfilProveedor> crearPerfilServidor(@RequestBody PerfilProveedor perfilServidor) {
        String email = perfilServidor.getEmail().toLowerCase();

        if (servicioPerfilServidor.obtenerPerfilServidor(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            PerfilProveedor savedPerfilServidor = servicioPerfilServidor.guardarPerfilServidor(perfilServidor);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPerfilServidor);
        }
      
    }
    
    @PutMapping("/perfil")
    public ResponseEntity<Optional<PerfilProveedor>> actualizarPerfilServidor(@RequestBody PerfilProveedor perfilServidor, Authentication authentication) {
        String email = ( (CustomUserDetails) authentication.getPrincipal() ).getEmail();
        Optional<PerfilProveedor> perfilActualizado = servicioPerfilServidor.actualizarPerfilServidor(email, perfilServidor);

        if (perfilActualizado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(perfilActualizado);
    }

    @PutMapping("/username")
    public ResponseEntity<PerfilProveedor> actualizarUsername(@RequestBody String username, Authentication authentication) {
        String email = ( (CustomUserDetails) authentication.getPrincipal() ).getEmail();
        //PerfilServidor perfilActualizado = servicioPerfilServidor.actualizarUsername(email, username);

        return ResponseEntity.ok(null);
    }


    public void agregarPortafolio(String email, List<Portafolio> nuevosPortafolios) {
        PerfilProveedor proveedor = servicioPerfilServidor.obtenerPerfilServidor(email)
            .orElseThrow(
                () -> new RuntimeException("Proveedor no encontrado")
            );
        
        if (proveedor.getPortafolios().size() >= MAX_PORTAFOLIOS) {
            throw new IllegalStateException("El proveedor ya tiene el máximo de " + MAX_PORTAFOLIOS + " portafolios");
        }
        
        for (Portafolio portafolio : nuevosPortafolios) {
            proveedor.addPortafolio(portafolio);
        }

        servicioPerfilServidor.guardarPerfilServidor(proveedor);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> eliminarPerfilServidor(@PathVariable String email) {
        Optional<PerfilProveedor> perfilServidor = servicioPerfilServidor.obtenerPerfilServidor(email);
        if (perfilServidor.isPresent()) {
            servicioPerfilServidor.eliminarPerfilServidor(email);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
