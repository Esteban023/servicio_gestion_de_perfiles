package com.worklink.profile_service.repository;

import com.worklink.profile_service.model.PerfilCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RepositorioPerfilCliente extends JpaRepository<PerfilCliente, Long> {

    Optional<PerfilCliente> findByUsuarioEmail(String email);

    void deleteByUsuarioEmail(String email);

}
