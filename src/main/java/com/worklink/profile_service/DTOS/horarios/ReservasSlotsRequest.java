package com.worklink.profile_service.DTOS.horarios;

import java.util.List;

public class ReservasSlotsRequest {
    private List<Long> idsSlots;
    private String codigoReserva;
    
    public List<Long> getIdsSlots() {
        return idsSlots;
    }
    public void setIdsSlots(List<Long> idsSlots) {
        this.idsSlots = idsSlots;
    }
    public String getCodigoReserva() {
        return codigoReserva;
    }
    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    
}
