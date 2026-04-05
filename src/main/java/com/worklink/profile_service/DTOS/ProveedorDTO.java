package com.worklink.profile_service.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProveedorDTO {

    @JsonProperty("verificado")
    boolean verificado;

    @JsonProperty("biografia")
    String biografia;

    @JsonProperty("activo")
    boolean activo;

    @JsonProperty("horario_disponibilidad")
    String horarioDisponibilidad;

    @JsonProperty("rating_promedio")
    Double ratingPromedio;

    public ProveedorDTO() {}

    public ProveedorDTO(Boolean verificado, String biografia, Boolean activo, String horarioDisponibilidad, Double ratingPromedio) {
        this.verificado = verificado;
        this.biografia = biografia;
        this.activo = activo;
        this.horarioDisponibilidad = horarioDisponibilidad;
        this.ratingPromedio = ratingPromedio;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public Double getRatingPromedio() {
        return ratingPromedio;
    }

    public void setRatingPromedio(Double ratingPromedio) {
        this.ratingPromedio = ratingPromedio;
    }

    public String getHorarioDisponibilidad() {
        return horarioDisponibilidad;
    }

    public void setHorarioDisponibilidad(String horarioDisponibilidad) {
        this.horarioDisponibilidad = horarioDisponibilidad;
    }



    



}
