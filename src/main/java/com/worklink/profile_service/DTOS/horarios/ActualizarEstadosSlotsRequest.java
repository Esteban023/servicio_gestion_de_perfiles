package com.worklink.profile_service.DTOS.horarios;


import java.util.List;

public class ActualizarEstadosSlotsRequest {


    List<Long> idsSlots;

    String estado;

    public List<Long> getIdsSlots() {
        return idsSlots;
    }

    public void setIdsSlots(List<Long> idsSlots) {
        this.idsSlots = idsSlots;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
