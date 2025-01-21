package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tipo_suelos")
public class Tipo_Suelo implements Serializable {

	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_tiposuelo;
    private String nombre_suelo;
    private String descripcion;
    private String color;
    private String textura;
    private String densidad;
    
    private static final long serialVersionUID = -5604445790837451384L;

	public Long getId_tiposuelo() {
		return id_tiposuelo;
	}

	public void setId_tiposuelo(Long id_tiposuelo) {
		this.id_tiposuelo = id_tiposuelo;
	}

	public String getNombre_suelo() {
		return nombre_suelo;
	}

	public void setNombre_suelo(String nombre_suelo) {
		this.nombre_suelo = nombre_suelo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getTextura() {
		return textura;
	}

	public void setTextura(String textura) {
		this.textura = textura;
	}

	public String getDensidad() {
		return densidad;
	}

	public void setDensidad(String densidad) {
		this.densidad = densidad;
	}

    

}
