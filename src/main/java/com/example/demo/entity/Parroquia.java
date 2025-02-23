package com.example.demo.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name="Parroquias",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_canton","id_parroquia"})})
public class Parroquia implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_parroquia;
	private Long id_canton;
	private String nombreParroquia;
	
	
	//relacion con usuarios
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_parroquia")
	@JsonBackReference
	private List<Usuarios> usuarios ; 
	
	//relacion con proyecto
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_parroquia")
	private List<Proyecto> proyecto  ; 
	
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
	
	public List<Usuarios> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuarios> usuarios) {
		this.usuarios = usuarios;
	}
	
	public List<Proyecto> getProyecto() {
		return proyecto;
	}
	public void setProyecto(List<Proyecto> proyecto) {
		this.proyecto = proyecto;
	}



	private static final long serialVersionUID = 1L;


}
