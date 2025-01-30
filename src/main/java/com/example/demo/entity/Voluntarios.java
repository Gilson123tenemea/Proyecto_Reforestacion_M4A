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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "voluntarios", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuarios","id_voluntario"})})
public class Voluntarios implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_voluntario;
	private String experiencia;
	private Boolean estado;
	private Boolean disponibilidad;
	private Boolean asiste_si_no = false;
	
	@Temporal(TemporalType.DATE)	
	private Date fechaRegistro;
	private Long id_usuarios;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_voluntario")
	private List<Inscripcion> inscripcion ; 
	
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_voluntario")
	private List<RegistroActividadRealiza> registroactividadrealisada  ; 
	
	

	public Long getId_voluntario() {
		return id_voluntario;
	}

	public void setId_voluntario(Long id_voluntario) {
		this.id_voluntario = id_voluntario;
	}

	public String getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(String experiencia) {
		this.experiencia = experiencia;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	public Long getId_usuarios() {
		return id_usuarios;
	}

	public void setId_usuarios(Long id_usuarios) {
		this.id_usuarios = id_usuarios;
	}
	
	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public Boolean getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(Boolean disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public List<Inscripcion> getInscripcion() {
		return inscripcion;
	}

	public void setInscripcion(List<Inscripcion> inscripcion) {
		this.inscripcion = inscripcion;
	}


	public Boolean getAsiste_si_no() {
		return asiste_si_no;
	}

	public void setAsiste_si_no(Boolean asiste_si_no) {
		this.asiste_si_no = asiste_si_no;
	}
	
	
	public List<RegistroActividadRealiza> getRegistroactividadrealisada() {
		return registroactividadrealisada;
	}

	public void setRegistroactividadrealisada(List<RegistroActividadRealiza> registroactividadrealisada) {
		this.registroactividadrealisada = registroactividadrealisada;
	}




	private static final long serialVersionUID = 1L;

}
