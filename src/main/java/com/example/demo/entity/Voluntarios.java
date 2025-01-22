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
@Table(name = "voluntarios", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuarios","id_proyecto_participacion","id_voluntario"})})
public class Voluntarios implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_voluntario;
	private String experiencia;
	
	@Temporal(TemporalType.DATE)	
	private Date fechaRegistro;
	
	//relacion con proyectos partivipacion
	private Long id_proyecto_participacion;
	
	//relacion con usuarios
	private Long id_usuarios;
	
	
	//relacion con asignacion equipo
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_voluntario")
	private List<Asignacion_equipo> asignacion_equipo ; 
	
	
	//relacion con inscripcion
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_voluntario")
	private List<Inscripcion> inscripcion ; 
		
		

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

	public Long getId_proyecto_participacion() {
		return id_proyecto_participacion;
	}

	public void setId_proyecto_participacion(Long id_proyecto_participacion) {
		this.id_proyecto_participacion = id_proyecto_participacion;
	}
	
	public Long getId_usuarios() {
		return id_usuarios;
	}

	public void setId_usuarios(Long id_usuarios) {
		this.id_usuarios = id_usuarios;
	}


	public List<Asignacion_equipo> getAsignacion_equipo() {
		return asignacion_equipo;
	}

	public void setAsignacion_equipo(List<Asignacion_equipo> asignacion_equipo) {
		this.asignacion_equipo = asignacion_equipo;
	}

	public List<Inscripcion> getInscripcion() {
		return inscripcion;
	}

	public void setInscripcion(List<Inscripcion> inscripcion) {
		this.inscripcion = inscripcion;
	}

	private static final long serialVersionUID = 1L;

}
