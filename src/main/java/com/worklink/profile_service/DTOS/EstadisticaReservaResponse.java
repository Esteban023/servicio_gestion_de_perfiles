package com.worklink.profile_service.DTOS;

import java.util.HashMap;

public class EstadisticaReservaResponse {
    HashMap<String, Double> porcentajePorServicio;
    HashMap<String, Double> porcentajePorEstado;
    Integer totalReservas;
    Double totalDineroGenerado;

    public EstadisticaReservaResponse(HashMap<String, Double> porcentajePorServicio, HashMap<String, Double> porcentajePorEstado, Integer totalReservas, Double totalDineroGenerado) {
        this.porcentajePorServicio = porcentajePorServicio;
        this.porcentajePorEstado = porcentajePorEstado;
        this.totalReservas = totalReservas;
        this.totalDineroGenerado = totalDineroGenerado;
    }

    public HashMap<String, Double> getPorcentajePorServicio() {
        return porcentajePorServicio;
    }

    public void setPorcentajePorServicio(HashMap<String, Double> porcentajePorServicio) {
        this.porcentajePorServicio = porcentajePorServicio;
    }

    public HashMap<String, Double> getPorcentajePorEstado() {
        return porcentajePorEstado;
    }

    public void setPorcentajePorEstado(HashMap<String, Double> porcentajePorEstado) {
        this.porcentajePorEstado = porcentajePorEstado;
    }

    public Integer getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(Integer totalReservas) {
        this.totalReservas = totalReservas;
    }

    public Double getTotalDineroGenerado() {
        return totalDineroGenerado;
    }

    public void setTotalDineroGenerado(Double totalDineroGenerado) {
        this.totalDineroGenerado = totalDineroGenerado;
    }
}
