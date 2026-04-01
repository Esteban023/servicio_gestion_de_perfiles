package com.worklink.profile_service.model;

import java.util.List;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Table(name = "perfil_cliente")
@Inheritance(strategy = InheritanceType.JOINED)

public class PerfilCliente {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "rating", nullable = false)
    private float rating;

    @Column(name = "phone", length = 20)
    private String phone;
    
    @OneToMany(mappedBy = "perfilCliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
    
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;
    
    @Column(name = "foto_perfil_url", length = 500)
    private String fotoPerfilUrl;
    
    @Column(name = "descripcion", length = 500)
    private String descripcion;    
       
    public PerfilCliente() {

    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Review> getReview() {
        return reviews;
    }

    public void setReview(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}