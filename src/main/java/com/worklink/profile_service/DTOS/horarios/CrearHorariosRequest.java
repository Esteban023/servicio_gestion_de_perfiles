package com.worklink.profile_service.DTOS.horarios;

import java.time.LocalTime;
import java.util.List;

public class CrearHorariosRequest {
    private List<Integer> diasLaborales;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Long idProveedor;

    public List<Integer> getDiasLaborales() {
        return diasLaborales;
    }

    public void setDiasLaborales(List<Integer> diasLaborales) {
        this.diasLaborales = diasLaborales;
    }


    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Long getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Long idProveedor) {
        this.idProveedor = idProveedor;
    }

}
