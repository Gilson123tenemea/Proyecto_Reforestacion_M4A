package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "usuario",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_parroquia","id_usuarios"})})
public class Usuarios implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_usuarios;
	
	private String cedula;
	private String nombre;
	private String apellido;
	private String correo;
	private Date fecha_nacimiento;
	private String genero;
	private String celular;
	private String contraseña;
	
	private Long id_parroquia;
	
    //relacion con super admiistrador
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_usuarios")
	private List<SuperAdministrador> super_administrador; 
	
	//relacion con voluntarios
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_usuarios")
	private List<Voluntarios> voluntarios; 
	
	//relacion con adminstrador
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_usuarios")
	private List<Administrador> administrador ; 
	
	//relacion con Ptrocinador
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_usuarios")
	private List<Patrocinador> patrocinador  ; 

		
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
