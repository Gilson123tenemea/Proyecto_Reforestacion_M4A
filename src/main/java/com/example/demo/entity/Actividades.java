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
	private String metareal;
	private String metadeseada;

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

	public String getMetareal() {
		return metareal;
	}

	public void setMetareal(String metareal) {
		this.metareal = metareal;
	}

	public String getMetadeseada() {
		return metadeseada;
	}

	public void setMetadeseada(String metadeseada) {
		this.metadeseada = metadeseada;
	}

	private static final long serialVersionUID = 1L;

}
