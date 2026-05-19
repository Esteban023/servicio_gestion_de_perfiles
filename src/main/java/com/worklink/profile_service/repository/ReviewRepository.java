package com.worklink.profile_service.repository;

import com.worklink.profile_service.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByClienteId(Long idCliente);
    List<Review> findByProveedorId(Long idProveedor);
    List<Review> findByServiceId(Long serviceId);

    @Query("""
        SELECT AVG(r.calificacion)
        FROM Review r
        WHERE r.proveedor.id = :proveedorId
    """)
    Double obtenerPromedioProveedor(
            @Param("proveedorId") Long proveedorId
    );
}
