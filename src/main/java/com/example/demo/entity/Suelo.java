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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="suelos",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_tiposuelo","id_suelo"})})
public class Suelo implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_suelo;
	private Long id_tiposuelo;
	private String composicion;
	private String descripcion;
	

	//relacion con parcelas
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_suelo")
	private List<Parcelas> parcelas; 
	
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
	
	public List<Parcelas> getParcelas() {
		return parcelas;
	}
	public void setParcelas(List<Parcelas> parcelas) {
		this.parcelas = parcelas;
	}

	private static final long serialVersionUID = 1L;
	
}
