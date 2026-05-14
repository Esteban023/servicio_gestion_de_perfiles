package com.worklink.profile_service.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import jakarta.persistence.*;

@Entity
@Table(name = "perfiles_proveedor")
public class Proveedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "usuario_email", referencedColumnName = "email", nullable = false)
    private Usuario usuario;
    
    @Column(name = "biografia", length = 100)
    private String biografia;
    
    @Column(name = "verificado", nullable = false)
    private boolean verificado;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @Column(name = "horario_disponibilidad", length = 500)
    private String horarioDisponibilidad;
    
    @Column(name = "rating_promedio")
    private Double ratingPromedio;
    
    // URLs de documentos
    @Column(name = "cedula_url")
    private String cedulaUrl;
    
    @Column(name = "certificado_salud_url")
    private String certificadoSaludUrl;
    
    @Column(name = "certificado_antecedentes_url")
    private String certificadoAntecedentesUrl;
    
    @Column(name = "certificado_inhabilidades_url")
    private String certificadoInhabilidadesUrl;
    
    @JsonIgnore
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
    
    public Proveedor() {
        this.verificado = false;
        this.ratingPromedio = 0.0;
    }
    
    public Proveedor(Usuario usuario) {
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
    
    public String getBiografia() {
        return biografia;
    }
    
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }
    
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isVerificado() {
        return verificado;
    }
    
    public void setVerificado(boolean verificado) {
        this.verificado = verificado;
    }
    
    public String getHorarioDisponibilidad() {
        return horarioDisponibilidad;
    }
    
    public void setHorarioDisponibilidad(String horarioDisponibilidad) {
        this.horarioDisponibilidad = horarioDisponibilidad;
    }
    
    public Double getRatingPromedio() {
        return ratingPromedio;
    }
    
    public void setRatingPromedio(Double ratingPromedio) {
        this.ratingPromedio = ratingPromedio;
    }
    
    public String getCedulaUrl() {
        return cedulaUrl;
    }
    
    public void setCedulaUrl(String cedulaUrl) {
        this.cedulaUrl = cedulaUrl;
    }
    
    public String getCertificadoSaludUrl() {
        return certificadoSaludUrl;
    }
    
    public void setCertificadoSaludUrl(String certificadoSaludUrl) {
        this.certificadoSaludUrl = certificadoSaludUrl;
    }
    
    public String getCertificadoAntecedentesUrl() {
        return certificadoAntecedentesUrl;
    }
    
    public void setCertificadoAntecedentesUrl(String certificadoAntecedentesUrl) {
        this.certificadoAntecedentesUrl = certificadoAntecedentesUrl;
    }
    
    public String getCertificadoInhabilidadesUrl() {
        return certificadoInhabilidadesUrl;
    }
    
    public void setCertificadoInhabilidadesUrl(String certificadoInhabilidadesUrl) {
        this.certificadoInhabilidadesUrl = certificadoInhabilidadesUrl;
    }
    
    public List<Review> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
    
//    // Método para agregar review
//    public void addReview(Review review) {
//        reviews.add(review);
//        review.setProveedor(this);
//    }
//
//    // Método para actualizar rating promedio
//    public void actualizarRatingPromedio() {
//        if (reviews.isEmpty()) {
//            this.ratingPromedio = 0.0;
//            return;
//        }
//
//        double promedio = reviews.stream()
//            .mapToInt(Review::getCalificacion)
//            .average()
//            .orElse(0.0);
//
//        this.ratingPromedio = promedio;
//    }
}