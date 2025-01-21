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
@Table(name = "Intervencion_Suelo")
public class Intervencion_Suelo implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_asignacionActividades;
	
	private Long id_actividades;
	private Long id_suelo;
	private Long Id_equipo;
	
	@Temporal(TemporalType.DATE)
	private Date fecha_asignacion;
	
	private String hora_asignacion;
	private String observaciones;
	
	public Long getId_asignacionActividades() {
		return id_asignacionActividades;
	}

	public void setId_asignacionActividades(Long id_asignacionActividades) {
		this.id_asignacionActividades = id_asignacionActividades;
	}

	public Long getId_actividades() {
		return id_actividades;
	}

	public void setId_actividades(Long id_actividades) {
		this.id_actividades = id_actividades;
	}

	public Long getId_suelo() {
		return id_suelo;
	}

	public void setId_suelo(Long id_suelo) {
		this.id_suelo = id_suelo;
	}

	public Long getId_equipo() {
		return Id_equipo;
	}

	public void setId_equipo(Long id_equipo) {
		Id_equipo = id_equipo;
	}

	public Date getFecha_asignacion() {
		return fecha_asignacion;
	}

	public void setFecha_asignacion(Date fecha_asignacion) {
		this.fecha_asignacion = fecha_asignacion;
	}

	public String getHora_asignacion() {
		return hora_asignacion;
	}

	public void setHora_asignacion(String hora_asignacion) {
		this.hora_asignacion = hora_asignacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	private static final long serialVersionUID = 1L;
	

}
