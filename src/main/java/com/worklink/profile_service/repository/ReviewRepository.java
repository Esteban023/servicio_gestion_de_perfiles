package com.worklink.profile_service.repository;

import com.worklink.profile_service.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByClienteId(Long idCliente);
    List<Review> findByProveedorId(Long idProveedor);
    List<Review> findByServiceId(Long serviceId);
}
