package com.example.demo.entity;

import java.io.Serializable;
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
@Table(name = "actividades",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_tipoActividades","id_actividades"})})
public class Actividades implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_actividades;
	
	private Long id_tipoActividades;
	private String estado;
	private String meta_real;
	private String meta_deseada;
	
	//relacion con incribre
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_actividades")
	private List<Inscripcion> inscripcion; 
	
	//relacion con intervencion_suelo
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_actividades")
	private List<Intervencion_Suelo> intervencion_suelo ; 
		
	//relacion con asignacionproyectoact
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_actividades")
	private List<Asignacion_proyectoActi> asignacion_proyectoacti ;
	
	//relacion con monitoreo
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_actividades")
	private List<Monitoreo> monitoreo ; 


	public Long getId_actividades() {
		return id_actividades;
	}

	public void setId_actividades(Long id_actividades) {
		this.id_actividades = id_actividades;
	}
	
	public Long getId_tipoActividades() {
		return id_tipoActividades;
	}

	public void setId_tipoActividades(Long id_tipoActividades) {
		this.id_tipoActividades = id_tipoActividades;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMeta_real() {
		return meta_real;
	}

	public void setMeta_real(String meta_real) {
		this.meta_real = meta_real;
	}

	public String getMeta_deseada() {
		return meta_deseada;
	}

	public void setMeta_deseada(String meta_deseada) {
		this.meta_deseada = meta_deseada;
	}
	
	public List<Inscripcion> getInscripcion() {
		return inscripcion;
	}

	public void setInscripcion(List<Inscripcion> inscripcion) {
		this.inscripcion = inscripcion;
	}
	

	public List<Intervencion_Suelo> getIntervencion_suelo() {
		return intervencion_suelo;
	}

	public void setIntervencion_suelo(List<Intervencion_Suelo> intervencion_suelo) {
		this.intervencion_suelo = intervencion_suelo;
	}

	public List<Asignacion_proyectoActi> getAsignacion_proyectoacti() {
		return asignacion_proyectoacti;
	}

	public void setAsignacion_proyectoacti(List<Asignacion_proyectoActi> asignacion_proyectoacti) {
		this.asignacion_proyectoacti = asignacion_proyectoacti;
	}
	
	public List<Monitoreo> getMonitoreo() {
		return monitoreo;
	}

	public void setMonitoreo(List<Monitoreo> monitoreo) {
		this.monitoreo = monitoreo;
	}

	private static final long serialVersionUID = 1L;

}
