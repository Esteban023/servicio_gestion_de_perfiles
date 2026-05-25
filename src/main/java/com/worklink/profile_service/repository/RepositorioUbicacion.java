package com.worklink.profile_service.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.worklink.profile_service.model.Ubicacion;

@Repository
public interface RepositorioUbicacion extends JpaRepository<Ubicacion, Long> {
    Optional<Ubicacion> findUbicationByClienteId(Long ClienteId);
}
