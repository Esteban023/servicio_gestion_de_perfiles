package com.worklink.profile_service.controller;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        String email = perfilServidor.getUsuario().getEmail().toLowerCase(); 

        if (servicioPerfilServidor.obtenerPerfilServidor(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        try {
            PerfilProveedor savedPerfilServidor = servicioPerfilServidor.guardarPerfilServidor(perfilServidor);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPerfilServidor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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
