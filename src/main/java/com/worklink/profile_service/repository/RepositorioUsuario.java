package com.worklink.profile_service.repository;

import com.worklink.profile_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RepositorioUsuario extends JpaRepository<Usuario, String> {

}
