package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "patrocinador")
public class Patrocinador implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id_patrocinador;

	private String cedula;
	private String nombreEmpresa;

	public Long getId_patrocinador() {
		return Id_patrocinador;
	}

	public void setId_patrocinador(Long id_patrocinador) {
		Id_patrocinador = id_patrocinador;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	private static final long serialVersionUID = 1L;

}
