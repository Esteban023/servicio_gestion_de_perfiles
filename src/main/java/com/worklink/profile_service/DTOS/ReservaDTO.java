package com.worklink.profile_service.DTOS;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservaDTO {

    String idReserva;

    private String rangoTiempoReservado; // Almacena algo como "09:00-11:00"


    String categoriaServicio;

    private String modalidad;

    String tituloServicio;

    LocalDate fechaReserva;

    Integer duracionServicio; //Duración en minutos. Minimo 30 minutos

    BigDecimal precio;

    Long proveedorId;

    String estadoReserva;

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getRangoTiempoReservado() {
        return rangoTiempoReservado;
    }

    public void setRangoTiempoReservado(String rangoTiempoReservado) {
        this.rangoTiempoReservado = rangoTiempoReservado;
    }

    public String getCategoriaServicio() {
        return categoriaServicio;
    }

    public void setCategoriaServicio(String categoriaServicio) {
        this.categoriaServicio = categoriaServicio;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getTituloServicio() {
        return tituloServicio;
    }

    public void setTituloServicio(String tituloServicio) {
        this.tituloServicio = tituloServicio;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Integer getDuracionServicio() {
        return duracionServicio;
    }

    public void setDuracionServicio(Integer duracionServicio) {
        this.duracionServicio = duracionServicio;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
}