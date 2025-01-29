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
@Table(name = "especie")
public class Especie implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_especie;

	private String nombre;
	 private boolean activo = true;
	
	//relacion con plantas
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_especie")
	private List<Plantas> plantas  ;

	public Long getId_especie() {
		return id_especie;
	}

	public void setId_especie(Long id_especie) {
		this.id_especie = id_especie;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	

	public List<Plantas> getPlantas() {
		return plantas;
	}

	public void setPlantas(List<Plantas> plantas) {
		this.plantas = plantas;
	}
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}



	private static final long serialVersionUID = 1L;

}
