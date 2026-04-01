package com.worklink.profile_service.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "rating", nullable = false) //Max 5, mínimo 1
    private Integer rating;
    
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @Column(name = "nombre_cliente", length = 100)
    private String nombreCliente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_email")
    private PerfilCliente perfilCliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_email")
    private PerfilProveedor perfilProveedor;

    public Review() {
        
    }

    public Review(Integer rating, LocalDate fecha, String descripcion, String nombreCliente) {
        this.rating = rating;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.nombreCliente = nombreCliente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public PerfilCliente getPerfilCliente() {
        return perfilCliente;
    }

    public void setPerfilCliente(PerfilCliente perfilCliente) {
        this.perfilCliente = perfilCliente;
    }
}
