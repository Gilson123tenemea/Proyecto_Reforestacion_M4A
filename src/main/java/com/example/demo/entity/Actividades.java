package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "actividades")
public class Actividades implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_actividades;
	
	private Long id_tipoactividades;
	private String estado;
	private String meta_real;
	private String meta_deseada;

	public Long getId_actividades() {
		return id_actividades;
	}

	public void setId_actividades(Long id_actividades) {
		this.id_actividades = id_actividades;
	}

	public Long getId_tipoactividades() {
		return id_tipoactividades;
	}

	public void setId_tipoactividades(Long id_tipoactividades) {
		this.id_tipoactividades = id_tipoactividades;
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



	private static final long serialVersionUID = 1L;

}
