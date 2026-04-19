package com.worklink.profile_service.repository;

import com.worklink.profile_service.model.HorariosSlots;
import com.worklink.profile_service.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HorariosSlotsRepository extends JpaRepository<HorariosSlots, Long> {

    @Query(value = "SELECT h.* FROM horarios_slots h "+
            "WHERE ("+
            "h.id_proveedor = ?2 "+
            "AND h.fecha = ?1 "+
            "AND LOWER(h.estado) = 'disponible'"+
            ") ORDER BY h.hora_inicio"
            , nativeQuery = true)
    List<HorariosSlots> obtenerPorFecha(LocalDate fecha, Long idProveedor);

    @Query(value = "SELECT DISTINCT h.fecha FROM horarios_slots h "+
            "WHERE ("+
            "h.id_proveedor = ?2 "+
            "AND h.fecha >= ?1 "+
            "AND LOWER(h.estado) = 'disponible'"+
            ")"
            , nativeQuery = true)
    List<LocalDate> obtenerFechas(LocalDate fecha, Long idProveedor);

    @Query(value = "SELECT h.* FROM horarios_slots h "+
            "WHERE ("+
            "h.id_proveedor = ?1 "+
            "AND NOT LOWER(h.estado) = 'no disponible')"
            , nativeQuery = true)
    List<HorariosSlots> obtenerTodos(Long idProveedor);

    boolean existsByProveedor(Proveedor proveedor);


}