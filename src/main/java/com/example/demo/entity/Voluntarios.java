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
	private Long id_voluntario;
	private String experiencia;
	
	@Temporal(TemporalType.DATE)	
	private Date fechaRegistro;
	
	//LLves FK
	private Long id_proyecto_participacion;
	
	

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

	
	private static final long serialVersionUID = 1L;

}
