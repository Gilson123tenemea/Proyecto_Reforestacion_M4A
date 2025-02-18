package com.example.demo.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_parroquia", "id_usuarios"})})
public class Usuarios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuarios;

    @NotNull(message = "La cédula no puede ser nula")
    @Pattern(regexp = "^[0-9]{10}$", message = "La cédula debe tener 10 dígitos")
    private String cedula;

    @NotNull(message = "El nombre no puede ser nulo")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    @NotNull(message = "El apellido no puede ser nulo")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "El apellido solo puede contener letras y espacios")
    private String apellido;



    @Email(message = "El correo debe tener un formato válido")
    @NotNull(message = "El correo no puede ser nulo")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "El correo debe tener un formato válido")
    private String correo;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "La fecha de nacimiento no puede ser nula")
    private Date fecha_nacimiento;

    private String genero;

    @NotNull(message = "El celular no puede ser nulo")
    @Pattern(regexp = "^[0-9]{10}$", message = "El celular debe tener 10 dígitos")
    private String celular;

    
    private String contraseña;


    private Long id_parroquia;

    // Relación con super administrador
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuarios")
    private List<SuperAdministrador> super_administrador;

    // Relación con voluntarios
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuarios")
    private List<Voluntarios> voluntarios;

    // Relación con administrador
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuarios")
    private List<Administrador> administrador;

    // Relación con patrocinador
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuarios")
    private List<Patrocinador> patrocinador;

    // Getters y Setters

    public Long getId_usuarios() {
        return id_usuarios;
    }

    public void setId_usuarios(Long id_usuarios) {
        this.id_usuarios = id_usuarios;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Long getId_parroquia() {
        return id_parroquia;
    }

    public void setId_parroquia(Long id_parroquia) {
        this.id_parroquia = id_parroquia;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public List<SuperAdministrador> getSuper_administrador() {
        return super_administrador;
    }

    public void setSuper_administrador(List<SuperAdministrador> super_administrador) {
        this.super_administrador = super_administrador;
    }

    public List<Voluntarios> getVoluntarios() {
        return voluntarios;
    }

    public void setVoluntarios(List<Voluntarios> voluntarios) {
        this.voluntarios = voluntarios;
    }

    public List<Administrador> getAdministrador() {
        return administrador;
    }

    public void setAdministrador(List<Administrador> administrador) {
        this.administrador = administrador;
    }

    public List<Patrocinador> getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(List<Patrocinador> patrocinador) {
        this.patrocinador = patrocinador;
    }

    private static final long serialVersionUID = 1L;
}
