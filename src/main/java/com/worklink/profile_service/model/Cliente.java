package com.worklink.profile_service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
@Table(name = "perfiles_cliente")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "usuario_email", referencedColumnName = "email", nullable = false)
    private Usuario usuario;
    
    @Column(name = "rating_promedio")
    private Double ratingPromedio;
    
    @Column(name = "ocupacion", length = 100)
    private String ocupacion;
    
    @Column(name = "activo", nullable = false)
    private boolean activo;

    @Column(name = "verificado", nullable = false)
    private boolean verificado;
    
    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
    
    public Cliente() {
        this.activo = true;
        this.verificado = false;
        this.ratingPromedio = 0.0;
    }
    
    public Cliente(Usuario usuario) {
        this();
        this.usuario = usuario;
    }
    
    // Getters y setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public Double getRating() {
        return ratingPromedio;
    }
    
    public void setRating(Double rating) {
        this.ratingPromedio = rating;
    }
    
    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public boolean isVerificado() {
        return verificado;
    }

    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }
    
    public List<Review> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
    public Double getRatingPromedio() {
        return ratingPromedio;
    }

    public void setRatingPromedio(Double ratingPromedio) {
        this.ratingPromedio = ratingPromedio;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Método para agregar review
    public void addReview(Review review) {
        reviews.add(review);
        review.setCliente(this);
    }
}