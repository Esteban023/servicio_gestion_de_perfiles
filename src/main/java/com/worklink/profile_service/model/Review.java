package com.worklink.profile_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "proveedor_id", referencedColumnName = "id")
    private Proveedor proveedor;
    
    @Column(name = "calificacion", nullable = false)
    private Double calificacion;
    
    @Column(name = "comentario", length = 1000)
    private String comentario;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    public Review() {
        this.fechaCreacion = LocalDateTime.now();
    }
    
    public Review(Double calificacion, String comentario) {
        this();
        this.calificacion = calificacion;
        this.comentario = comentario;
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Proveedor getProveedor() {
        return proveedor;
    }
    
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    
    public Double getCalificacion() {
        return calificacion;
    }
    
    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
    
    public String getComentario() {
        return comentario;
    }
    
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}