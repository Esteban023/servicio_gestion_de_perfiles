package com.worklink.profile_service.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.worklink.profile_service.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepositorioPerfilCliente extends JpaRepository<Cliente, Long> {

    @Query("SELECT perfil FROM Cliente perfil WHERE perfil.usuario.email = :email")
    Optional<Cliente> findByUsuarioEmail(@Param("email") String email);

    void deleteByUsuarioEmail(@Param("email") String email);

}
