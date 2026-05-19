package com.worklink.profile_service.model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    // ─── Datos bancarios colombianos ───────────────
    @Column(name = "cuenta_bancaria_titular")
    private String titular;

    @Column(name = "cuenta_bancaria_numero")
    private String numeroCuenta;

    @Column(name = "cuenta_bancaria_tipo")
    private String tipoCuenta;       // "AHORROS", "CORRIENTE"

    @Column(name = "cuenta_bancaria_banco")
    private String banco;            // "Bancolombia", "Davivienda"...

    @Column(name = "cuenta_bancaria_documento")
    private String documento;        // cédula del titular de la cuenta

    @Column(name = "cuenta_vinculada")
    private Boolean cuentaVinculada;

    @Column(name = "cuenta_vinculada_at")
    private LocalDateTime cuentaVinculadaAt;
    // ───────────────────────────────────────────────

    @JsonIgnore
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Ubicacion ubicacion;

    public Proveedor() {
        this.verificado = false;
        this.ratingPromedio = 0.0;
        this.cuentaVinculada = false;
        this.cuentaVinculadaAt = null;
    }

    public Proveedor(Usuario usuario) {
        this();
        this.usuario = usuario;
    }

    public void addReview(Review review) {
        reviews.add(review);
        review.setProveedor(this);
    }

    public void actualizarRatingPromedio() {
        if (reviews.isEmpty()) {
            this.ratingPromedio = 0.0;
            return;
        }
        this.ratingPromedio = reviews.stream()
            .mapToDouble(review -> review.getCalificacion())
            .average()
            .orElse(0.0);
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }

    public boolean isVerificado() { return verificado; }
    public void setVerificado(boolean verificado) { this.verificado = verificado; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public String getHorarioDisponibilidad() { return horarioDisponibilidad; }
    public void setHorarioDisponibilidad(String h) { this.horarioDisponibilidad = h; }

    public Double getRatingPromedio() { return ratingPromedio; }
    public void setRatingPromedio(Double r) { this.ratingPromedio = r; }

    public String getCedulaUrl() { return cedulaUrl; }
    public void setCedulaUrl(String cedulaUrl) { this.cedulaUrl = cedulaUrl; }

    public String getCertificadoSaludUrl() { return certificadoSaludUrl; }
    public void setCertificadoSaludUrl(String url) { this.certificadoSaludUrl = url; }

    public String getCertificadoAntecedentesUrl() { return certificadoAntecedentesUrl; }
    public void setCertificadoAntecedentesUrl(String url) { this.certificadoAntecedentesUrl = url; }

    public String getCertificadoInhabilidadesUrl() { return certificadoInhabilidadesUrl; }
    public void setCertificadoInhabilidadesUrl(String url) { this.certificadoInhabilidadesUrl = url; }

    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public String getTipoCuenta() { return tipoCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }

    public String getBanco() { return banco; }
    public void setBanco(String banco) { this.banco = banco; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public Boolean getCuentaVinculada() { return cuentaVinculada; }
    public void setCuentaVinculada(Boolean cuentaVinculada) { this.cuentaVinculada = cuentaVinculada; }

    public LocalDateTime getCuentaVinculadaAt() { return cuentaVinculadaAt; }
    public void setCuentaVinculadaAt(LocalDateTime at) { this.cuentaVinculadaAt = at; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    public Ubicacion getUbicacion() { return ubicacion; }
    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }
}