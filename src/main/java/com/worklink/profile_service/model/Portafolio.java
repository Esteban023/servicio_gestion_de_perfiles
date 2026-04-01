package com.worklink.profile_service.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Portafolio {

    private Long id;
    private String titulo;
    private String imagenUrl;
    private LocalDate fechaTrabajo;
    private String descripcion;
    private boolean verificado;

    @JsonIgnore
    private PerfilProveedor perfilProveedor;

    public Portafolio() {}

    public Portafolio(String titulo, String imagenUrl, String descripcion, boolean verificado, LocalDate fechaTrabajo) {
        this.titulo = titulo;
        this.imagenUrl = imagenUrl;
        this.descripcion = descripcion;
        this.verificado = verificado;
        this.fechaTrabajo = fechaTrabajo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }

    public LocalDate getFechaTrabajo() {
        return fechaTrabajo;
    }

    public void setFechaTrabajo(LocalDate fechaTrabajo) {
        this.fechaTrabajo = fechaTrabajo;
    }

    public PerfilProveedor getPerfilProveedor() {
        return perfilProveedor;
    }

    public void setPerfilProveedor(PerfilProveedor perfilProveedor) {
        this.perfilProveedor = perfilProveedor;
    }

    
}