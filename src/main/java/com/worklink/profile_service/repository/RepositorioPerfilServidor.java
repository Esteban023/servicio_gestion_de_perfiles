package com.worklink.profile_service.repository;

import org.springframework.stereotype.Repository;
import com.worklink.profile_service.model.PerfilProveedor;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface RepositorioPerfilServidor extends JpaRepository<PerfilProveedor, String> {

}
