package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "areas")
public class Area implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_area;
	
	private Long id_proyecto;
	private Long id_parcelas;
	
	private String tipo_terreno;
	private String tipo_vegetacion;
	private String observaciones;
	
	
	public Long getId_area() {
		return id_area;
	}


	public void setId_area(Long id_area) {
		this.id_area = id_area;
	}


	public Long getId_proyecto() {
		return id_proyecto;
	}


	public void setId_proyecto(Long id_proyecto) {
		this.id_proyecto = id_proyecto;
	}


	public Long getId_parcelas() {
		return id_parcelas;
	}


	public void setId_parcelas(Long id_parcelas) {
		this.id_parcelas = id_parcelas;
	}


	public String getTipo_terreno() {
		return tipo_terreno;
	}


	public void setTipo_terreno(String tipo_terreno) {
		this.tipo_terreno = tipo_terreno;
	}


	public String getTipo_vegetacion() {
		return tipo_vegetacion;
	}


	public void setTipo_vegetacion(String tipo_vegetacion) {
		this.tipo_vegetacion = tipo_vegetacion;
	}


	public String getObservaciones() {
		return observaciones;
	}


	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}


	private static final long serialVersionUID = 1L;

}
