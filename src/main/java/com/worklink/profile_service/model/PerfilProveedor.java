package com.worklink.profile_service.model;

import java.util.List;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;


@Entity
@Table(name = "perfil_proveedor")
public class PerfilProveedor extends PerfilCliente {
    
    @OneToMany(mappedBy = "perfilProveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
    
    @Column(name = "biografia", length = 1000)
    private String biografia;
    
    @Column(name = "verificado", nullable = false)
    private boolean verificado;
    
    @Column(name = "horario_disponibilidad", length = 500)
    private String horarioDisponibilidad;

    // Antecedentes legales y certificaciones
    @Column(name = "cedula") //Imagen de la cédula
    private String cedulaUrl;
    
    @Column(name = "medidas_correctivas", length = 500) //Medidas correctivas
    private String medidasCorrectivasUrl;
    
    @Column(name = "certificado_salud") //Imagen del certificado de salud
    private String certificadoSaludUrl;
    
    @Column(name = "certificado_inhabilidades") //Antecedentes delitos sexuales
    private String certificadoInhabilidaesUrl;
    
    @Column(name = "certificado_antecedentes") //Imagen del certificado de antecedentes
    private String certificadoAntecedentesUrl;
    
    public PerfilProveedor() {}


    public PerfilProveedor(List<Review> reviews, String biografia, boolean verificado, String horarioDisponibilidad, String cedulaUrl, String medidasCorrectivasUrl,
        String certificadoSaludUrl, String certificadoInhabilidaesUrl, String certificadoAntecedentesUrl) 
    {
        this.reviews = reviews;
        this.biografia = biografia;
        this.cedulaUrl = cedulaUrl;
        this.verificado = verificado;
        this.certificadoSaludUrl = certificadoSaludUrl;
        this.horarioDisponibilidad = horarioDisponibilidad;
        this.medidasCorrectivasUrl = medidasCorrectivasUrl;
        this.certificadoInhabilidaesUrl = certificadoInhabilidaesUrl;
        this.certificadoAntecedentesUrl = certificadoAntecedentesUrl;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
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

}
