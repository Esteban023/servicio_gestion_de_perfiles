package com.worklink.profile_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.worklink.profile_service.model.Usuario;
import com.worklink.profile_service.repository.RepositorioUsuario;

import org.springframework.web.bind.annotation.PostMapping;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        repositorioUsuario.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }


}