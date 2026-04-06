package com.worklink.profile_service.controller;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.worklink.profile_service.DTOS.ProveedorDTO;
import com.worklink.profile_service.Mappers.ProveedorMapper;
import com.worklink.profile_service.model.Proveedor;
import com.worklink.profile_service.repository.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import com.worklink.profile_service.services.ServicioPerfilServidor;


@RestController
@RequestMapping("/api/perfil-servidor")
public class PerfilServidorController {

    @Autowired
    private RepositorioUsuario repoUsuario;

    @Autowired
    private ServicioPerfilServidor servicioPerfilServidor;

    @PostMapping
    public ResponseEntity<Proveedor> crearPerfilServidor(@RequestBody Proveedor perfilServidor) {
        String email = perfilServidor.getUsuario().getEmail().toLowerCase(); 

        if (repoUsuario.findById(email).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        try {
            Proveedor savedPerfilServidor = servicioPerfilServidor.guardarPerfilServidor(perfilServidor);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPerfilServidor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
      
    }

    @GetMapping("/{email}")
    public ResponseEntity<ProveedorDTO> obtenerPerfilServidor(@PathVariable String email) {
        Optional<Proveedor> perfilServidor = servicioPerfilServidor.obtenerPerfilServidorPorEmail(
            email.toLowerCase()
        );
        
        if (perfilServidor.isPresent()) {
            return ResponseEntity.ok(ProveedorMapper.toDto(perfilServidor.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
  
    }

    @PutMapping("/{email}")
    public ResponseEntity<Proveedor> actualizarPerfilServidor(@PathVariable String email, @RequestBody Proveedor perfilServidorDetails) {
        Proveedor updatedPerfilServidor = servicioPerfilServidor.actualizarPerfilServidor(email, perfilServidorDetails);

        if (updatedPerfilServidor == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedPerfilServidor);
    }
    

}
