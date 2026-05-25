package com.worklink.profile_service.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UbicacionDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("latitud")
    private Double latitud;

    @JsonProperty("longitud")
    private Double longitud;

    @JsonProperty("direccion")
    private String direccion;

    @JsonProperty("ciudad")
    private String ciudad;

    @JsonProperty("departamento")
    private String departamento;

    // Constructor vacío
    public UbicacionDTO() {
    }

    // Constructor con parámetros
    public UbicacionDTO(Long id, Double latitud, Double longitud, String direccion, String ciudad, String departamento) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.departamento = departamento;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
