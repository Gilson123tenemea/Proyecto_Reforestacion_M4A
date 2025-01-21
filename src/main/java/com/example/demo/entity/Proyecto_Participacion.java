package com.example.demo.entity;
import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "proyectoparticipacion")
public class Proyecto_Participacion implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_proyecto_participacion;
	private String nomnbre;
	private String actividades;
	private Date fechaparticipo;
	
	

	public Long getId_proyecto_participacion() {
		return id_proyecto_participacion;
	}

	public void setId_proyecto_participacion(Long id_proyecto_participacion) {
		this.id_proyecto_participacion = id_proyecto_participacion;
	}

	public String getNomnbre() {
		return nomnbre;
	}

	public void setNomnbre(String nomnbre) {
		this.nomnbre = nomnbre;
	}

	public String getActividades() {
		return actividades;
	}

	public void setActividades(String actividades) {
		this.actividades = actividades;
	}

	public Date getFechaparticipo() {
		return fechaparticipo;
	}

	public void setFechaparticipo(Date fechaparticipo) {
		this.fechaparticipo = fechaparticipo;
	}

	private static final long serialVersionUID = 1L;

}
