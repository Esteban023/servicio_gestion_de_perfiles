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
import com.worklink.profile_service.DTOS.ClienteDTO;
import com.worklink.profile_service.Mappers.ClienteMapper;
import com.worklink.profile_service.model.Cliente;
import com.worklink.profile_service.repository.RepositorioUsuario;
import com.worklink.profile_service.services.ServicioPerfilCliente;


@RestController
@RequestMapping("/api/perfil-cliente")
public class PerfilClienteController {
    @Autowired
    private ServicioPerfilCliente servicioPerfilCliente;

    @Autowired
    private RepositorioUsuario repoUsuario;

    @GetMapping
    public List<Cliente> getAllPerfilClientes() {
        return servicioPerfilCliente.obtenerTodosLosPerfilesClientes();
    }

    @GetMapping("/{email}")
    public ResponseEntity<ClienteDTO> getPerfilClienteByEmail(@PathVariable String email) {
        Optional<Cliente> perfilClienteOpt = servicioPerfilCliente.obtenerPerfilCliente(email.toLowerCase());

        if (perfilClienteOpt.isPresent()) {
            return ResponseEntity.ok(
                ClienteMapper.toDto(perfilClienteOpt.get())
            );
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }

    @GetMapping("/i/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Long id) {
        Optional<Cliente> optional = servicioPerfilCliente.obtenerPerfilClientePorId(id);
        if(optional.isPresent()){
            return ResponseEntity.ok(optional.get());
        }
        return ResponseEntity.notFound().build();
    }
    

    @PostMapping
    public ResponseEntity<Cliente> createPerfilCliente(@RequestBody Cliente cliente) {
        if (cliente == null || cliente.getUsuario() == null || cliente.getUsuario().getEmail() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String email = cliente.getUsuario().getEmail().toLowerCase();

        if (repoUsuario.findById(email).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            Cliente savedCliente = servicioPerfilCliente.guardarPerfilCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PutMapping("/{email}")
    public ResponseEntity<Cliente> updatePerfilCliente(@PathVariable String email, @RequestBody Cliente perfilClienteDetails) {
        Cliente updatedPerfilCliente = servicioPerfilCliente.actualizarPerfilCliente(email, perfilClienteDetails);

        if (updatedPerfilCliente == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedPerfilCliente);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletePerfilCliente(@PathVariable String email) {
        Optional<Cliente> perfilCliente = servicioPerfilCliente.obtenerPerfilCliente(email);
        if (perfilCliente.isPresent()) {
            servicioPerfilCliente.eliminarPerfilCliente(email);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
