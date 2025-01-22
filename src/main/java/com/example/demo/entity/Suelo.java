package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="suelos")
public class Suelo implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_suelo;
	private Long id_tiposuelo;
	private String composicion;
	private String descripcion;
	public Long getId_suelo() {
		return id_suelo;
	}
	public void setId_suelo(Long id_suelo) {
		this.id_suelo = id_suelo;
	}
	public Long getId_tiposuelo() {
		return id_tiposuelo;
	}
	public void setId_tiposuelo(Long id_tiposuelo) {
		this.id_tiposuelo = id_tiposuelo;
	}
	public String getComposicion() {
		return composicion;
	}
	public void setComposicion(String composicion) {
		this.composicion = composicion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

	
	
	

	
	
	
	

}
