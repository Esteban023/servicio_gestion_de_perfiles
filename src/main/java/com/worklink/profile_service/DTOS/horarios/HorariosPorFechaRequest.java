package com.worklink.profile_service.DTOS.horarios;

import java.time.LocalDate;

public class HorariosPorFechaRequest {
    LocalDate fecha;
    Long idProveedor;
    Integer duracionServ;

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getDuracionServ() {
        return duracionServ;
    }

    public void setDuracionServ(Integer duracionServ) {
        this.duracionServ = duracionServ;
    }
}
