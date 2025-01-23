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


@Entity
@Table(name="tipo_actividades")
public class Tipo_Actividades implements Serializable{

	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    private Long id_tipoActividades;
    private String nombre_act;
    private Double duracion;
    private String frecuencia;
    private String descripcion;
    
    
  //relacion con monitoreo
  	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  	@JoinColumn(name ="id_tipoActividades")
  	private List<Monitoreo> monitoreo  ; 
  	
  //relacion con asignacion_proyecto
  	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  	@JoinColumn(name ="id_tipoActividades")
  	private List<Asignacion_proyectoActi> asignacion_proyectoacti   ; 
    
   //relacion con intervencion_suelo
   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name ="id_tipoActividades")
   private List<Intervencion_Suelo> intervencionsuelo   ; 
  	
    private static final long serialVersionUID = 1L;
    
    public Long getId_tipoActividades() {
		return id_tipoActividades;
	}

	public void setId_tipoActividades(Long id_tipoActividades) {
		this.id_tipoActividades = id_tipoActividades;
	}



	public String getNombre_act() {
		return nombre_act;
	}



	public void setNombre_act(String nombre_act) {
		this.nombre_act = nombre_act;
	}



	public Double getDuracion() {
		return duracion;
	}



	public void setDuracion(Double duracion) {
		this.duracion = duracion;
	}



	public String getFrecuencia() {
		return frecuencia;
	}



	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Monitoreo> getMonitoreo() {
		return monitoreo;
	}

	public void setMonitoreo(List<Monitoreo> monitoreo) {
		this.monitoreo = monitoreo;
	}

	public List<Asignacion_proyectoActi> getAsignacion_proyectoacti() {
		return asignacion_proyectoacti;
	}

	public void setAsignacion_proyectoacti(List<Asignacion_proyectoActi> asignacion_proyectoacti) {
		this.asignacion_proyectoacti = asignacion_proyectoacti;
	}

	public List<Intervencion_Suelo> getIntervencionsuelo() {
		return intervencionsuelo;
	}

	public void setIntervencionsuelo(List<Intervencion_Suelo> intervencionsuelo) {
		this.intervencionsuelo = intervencionsuelo;
	}
	

}
