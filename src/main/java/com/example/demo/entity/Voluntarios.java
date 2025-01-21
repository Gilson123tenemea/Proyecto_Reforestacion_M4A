package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "voluntarios")
public class Voluntarios implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id_valuntario;
	private String experiencia;
	
	@Temporal(TemporalType.DATE)	
	private Date fechaRegistro;
	
	//LLves FK
	private Long id_proyecto_participacion;
	private Long id_equipo;
	
	
	
	public Long getId_valuntario() {
		return Id_valuntario;
	}

	public void setId_valuntario(Long id_valuntario) {
		Id_valuntario = id_valuntario;
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

	public Long getId_equipo() {
		return id_equipo;
	}

	public void setId_equipo(Long id_equipo) {
		this.id_equipo = id_equipo;
	}

	private static final long serialVersionUID = 1L;

}
