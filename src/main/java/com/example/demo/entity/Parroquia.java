package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Parroquias")
public class Parroquia implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_parroquia;
	private Long id_canton;
	private String nombreParroquia;
	public Long getId_parroquia() {
		return id_parroquia;
	}
	public void setId_parroquia(Long id_parroquia) {
		this.id_parroquia = id_parroquia;
	}
	public Long getId_canton() {
		return id_canton;
	}
	public void setId_canton(Long id_canton) {
		this.id_canton = id_canton;
	}
	public String getNombreParroquia() {
		return nombreParroquia;
	}
	public void setNombreParroquia(String nombreParroquia) {
		this.nombreParroquia = nombreParroquia;
	}
	
	
	

}
