package com.example.demo.entity;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    
    private static final long serialVersionUID = -7829507919417151416L;
    
    
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



	
}
