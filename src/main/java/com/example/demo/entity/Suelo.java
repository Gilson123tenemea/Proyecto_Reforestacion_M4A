package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="suelo")
public class Suelo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_suelo;
	private Long id_tiposuelo;
	private double ph;
	private String composicion;
	private boolean fertilidad;
	public long getId_suelo() {
		return id_suelo;
	}
	public void setId_suelo(long id_suelo) {
		this.id_suelo = id_suelo;
	}
	public Long getId_tiposuelo() {
		return id_tiposuelo;
	}
	public void setId_tiposuelo(Long id_tiposuelo) {
		this.id_tiposuelo = id_tiposuelo;
	}
	public double getPh() {
		return ph;
	}
	public void setPh(double ph) {
		this.ph = ph;
	}
	public String getComposicion() {
		return composicion;
	}
	public void setComposicion(String composicion) {
		this.composicion = composicion;
	}
	public boolean isFertilidad() {
		return fertilidad;
	}
	public void setFertilidad(boolean fertilidad) {
		this.fertilidad = fertilidad;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	

	
	
	
	

}
