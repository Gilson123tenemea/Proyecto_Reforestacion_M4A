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
@Table(name="cantones",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_provincia","id_canton"})})

public class Canton implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id_canton;
	private Long id_provincia;
	private String nombreCanton;
	
	//relacion con canton
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_canton")
	private List<Parroquia> parroquia  ; 
	
	public Long getId_canton() {
		return id_canton;
	}
	public void setId_canton(Long id_canton) {
		this.id_canton = id_canton;
	}
	public Long getId_provincia() {
		return id_provincia;
	}
	public void setId_provincia(Long id_provincia) {
		this.id_provincia = id_provincia;
	}
	public String getNombreCanton() {
		return nombreCanton;
	}
	public void setNombreCanton(String nombreCanton) {
		this.nombreCanton = nombreCanton;
	}
	
	public List<Parroquia> getParroquia() {
		return parroquia;
	}
	public void setParroquia(List<Parroquia> parroquia) {
		this.parroquia = parroquia;
	}

	private static final long serialVersionUID = 1L;

}
