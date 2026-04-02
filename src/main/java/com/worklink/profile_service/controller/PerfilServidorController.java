package com.worklink.profile_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.worklink.profile_service.model.PerfilProveedor;
import com.worklink.profile_service.repository.RepositorioUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import com.worklink.profile_service.services.ServicioPerfilServidor;

@RestController
@RequestMapping("/api/perfil-servidor")
public class PerfilServidorController {
    @Autowired
    private ServicioPerfilServidor servicioPerfilServidor;

    @Autowired
    private RepositorioUsuario repoUsuario;

    @PostMapping
    public ResponseEntity<PerfilProveedor> crearPerfilServidor(@RequestBody PerfilProveedor perfilServidor) {
        String email = perfilServidor.getUsuario().getEmail().toLowerCase(); 

        if (repoUsuario.findById(email).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        try {
            PerfilProveedor savedPerfilServidor = servicioPerfilServidor.guardarPerfilServidor(perfilServidor);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPerfilServidor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
      
    }
    

}
