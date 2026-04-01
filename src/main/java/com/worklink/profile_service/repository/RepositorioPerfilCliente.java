package com.worklink.profile_service.repository;

import com.worklink.profile_service.model.PerfilCliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioPerfilCliente extends JpaRepository<PerfilCliente, String> {

}
