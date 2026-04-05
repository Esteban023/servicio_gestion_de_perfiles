package com.worklink.profile_service.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import com.worklink.profile_service.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface RepositorioPerfilServidor extends JpaRepository<Proveedor, Long> {
    @Query("""
        SELECT perfil
        FROM Proveedor perfil 
        WHERE perfil.usuario.email = :email
    """)
    Optional<Proveedor> findByUsuarioEmail(@Param("email") String email);

    
}
