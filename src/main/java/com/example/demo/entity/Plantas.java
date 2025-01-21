package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "plantas")
public class Plantas implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_plantas;

	private Long id_especie;

	private String nombre_cientifico;
	private String clima;
	private String nombre_comun;
	private Double tamaño;
	private String color_hojas;

	public Long getId_plantas() {
		return id_plantas;
	}

	public void setId_plantas(Long id_plantas) {
		this.id_plantas = id_plantas;
	}

	public Long getId_especie() {
		return id_especie;
	}

	public void setId_especie(Long id_especie) {
		this.id_especie = id_especie;
	}

	public String getNombre_cientifico() {
		return nombre_cientifico;
	}

	public void setNombre_cientifico(String nombre_cientifico) {
		this.nombre_cientifico = nombre_cientifico;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public String getNombre_comun() {
		return nombre_comun;
	}

	public void setNombre_comun(String nombre_comun) {
		this.nombre_comun = nombre_comun;
	}

	public Double getTamaño() {
		return tamaño;
	}

	public void setTamaño(Double tamaño) {
		this.tamaño = tamaño;
	}

	public String getColor_hojas() {
		return color_hojas;
	}

	public void setColor_hojas(String color_hojas) {
		this.color_hojas = color_hojas;
	}

	private static final long serialVersionUID = 1L;

}
