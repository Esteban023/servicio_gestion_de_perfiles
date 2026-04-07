package com.worklink.profile_service.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClienteDTO {

    @JsonProperty("usuario")
    private UsuarioDTO usuario;

    @JsonProperty("ocupacion")
    String ocupacion;

    @JsonProperty("activo")
    boolean activo;

    @JsonProperty("verificado")
    boolean verificado;

    @JsonProperty("ratingPromedio")
    Double ratingPromedio;

    public ClienteDTO() {}

    public ClienteDTO(UsuarioDTO usuario, Boolean activo, Boolean verificado, Double ratingPromedio, String ocupacion) {
        this.usuario = usuario;
        this.activo = activo;
        this.ocupacion = ocupacion;
        this.verificado = verificado;
        this.ratingPromedio = ratingPromedio;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    public Double getRatingPromedio() {
        return ratingPromedio;
    }

    public void setRatingPromedio(Double ratingPromedio) {
        this.ratingPromedio = ratingPromedio;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

}
