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
@Table(name="provincias")

public class Provincia implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_provincia;
	private String nombreProvincia;
	
	//relacion con canton
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_provincia")
	private List<Canton> canton ; 
	
	
	public Long getId_provincia() {
		return id_provincia;
	}
	public void setId_provincia(Long id_provincia) {
		this.id_provincia = id_provincia;
	}
	public String getNombreProvincia() {
		return nombreProvincia;
	}
	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}
	
	public List<Canton> getCanton() {
		return canton;
	}
	public void setCanton(List<Canton> canton) {
		this.canton = canton;
	}

	private static final long serialVersionUID = 1L;


}
