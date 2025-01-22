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
@Table(name = "superadmin", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuarios","id_super_administrador"})})
public class SuperAdministrador implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_super_administrador;

	//relacion con usuarios
	private Long id_usuarios;

	private Date fechaInicio;
	private Date fechaFin;

	
	//relacion con super administrador
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_super_administrador")
	private List<Administrador> administrador; 
	
	
	
		
	public Long getId_super_administrador() {
		return id_super_administrador;
	}

	public void setId_super_administrador(Long id_super_administrador) {
		this.id_super_administrador = id_super_administrador;
	}

	public Long getId_usuarios() {
		return id_usuarios;
	}

	public void setId_usuarios(Long id_usuarios) {
		this.id_usuarios = id_usuarios;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	

	public List<Administrador> getAdministrador() {
		return administrador;
	}

	public void setAdministrador(List<Administrador> administrador) {
		this.administrador = administrador;
	}



	private static final long serialVersionUID = 1L;

}
