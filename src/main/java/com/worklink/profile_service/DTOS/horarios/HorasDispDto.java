package com.worklink.profile_service.DTOS.horarios;

import java.time.LocalTime;

public class HorasDispDto {
    LocalTime horaInicio;
    LocalTime horaFin;
    String idsSlots;


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

    public String getIdsSlots() {
        return idsSlots;
    }

    public void setIdsSlots(String idsSlots) {
        this.idsSlots = idsSlots;
    }


}
