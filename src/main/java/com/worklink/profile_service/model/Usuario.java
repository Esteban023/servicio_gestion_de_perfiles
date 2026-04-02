package com.worklink.profile_service.model;


import java.time.LocalDate;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @JsonProperty("email") // Para mapear el campo "email" del JSON al atributo "email" de la clase
    @Column(name = "email", nullable = false, length = 100)
    private String email;  // Mismo email del auth service
    
    @JsonProperty("nombre")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @JsonProperty("apellido")
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;
    
    @JsonProperty("telefono")   
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @JsonProperty("fotoPerfilUrl")
    @Column(name = "foto_perfil_url", length = 500)
    private String fotoPerfilUrl;
    
    @JsonProperty("fechaNacimiento")
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @JsonProperty("fechaRegistro")
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    

    // Relaciones con perfiles
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private PerfilCliente perfilCliente;
    
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private PerfilProveedor perfilProveedor;
    
    public Usuario() {
        this.fechaRegistro = LocalDateTime.now();
    }
    
    // Constructor para creación inicial
    public Usuario(String email, String nombre, String apellido, String telefono) {
        this();
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }
    
    // Getters y setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
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
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }
    
    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }
    
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public PerfilCliente getPerfilCliente() {
        return perfilCliente;
    }
    
    public void setPerfilCliente(PerfilCliente perfilCliente) {
        this.perfilCliente = perfilCliente;
        if (perfilCliente != null) {
            perfilCliente.setUsuario(this);
        }
    }
    
    public PerfilProveedor getPerfilProveedor() {
        return perfilProveedor;
    }
    
    public void setPerfilProveedor(PerfilProveedor perfilProveedor) {
        this.perfilProveedor = perfilProveedor;
        if (perfilProveedor != null) {
            perfilProveedor.setUsuario(this);
        }
    }
    
    // Métodos de utilidad
    public boolean esProveedor() {
        return perfilProveedor != null;
    }
    
    public boolean esCliente() {
        return perfilCliente != null;
    }
}