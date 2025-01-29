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
@Table(name = "patrocinador", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuarios","Id_patrocinador"})})
public class Patrocinador implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id_patrocinador;

	//Relacion con usuarios
	private Long id_usuarios;
	private String nombreEmpresa;
	private String ruc;
	
	//relacion con patrocinio
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="Id_patrocinador")
	private List<Patrocinio> patrocinio; 
		

	public Long getId_patrocinador() {
		return Id_patrocinador;
	}

	public void setId_patrocinador(Long id_patrocinador) {
		Id_patrocinador = id_patrocinador;
	}


	public Long getId_usuarios() {
		return id_usuarios;
	}

	public void setId_usuarios(Long id_usuarios) {
		this.id_usuarios = id_usuarios;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}


	public List<Patrocinio> getPatrocinio() {
		return patrocinio;
	}

	public void setPatrocinio(List<Patrocinio> patrocinio) {
		this.patrocinio = patrocinio;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}


	private static final long serialVersionUID = 1L;

}
