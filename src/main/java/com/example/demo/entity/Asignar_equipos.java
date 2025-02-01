package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "asignar_equipos", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_voluntario","id_equipos","id_asignar_equipos"})})
public class Asignar_equipos implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_asignar_equipos;
	
	private Long id_voluntario;
	private Long id_equipos;
	private Date fecha_asinacionequi;
	
	
	
	public Long getId_asignar_equipos() {
		return id_asignar_equipos;
	}



	public void setId_asignar_equipos(Long id_asignar_equipos) {
		this.id_asignar_equipos = id_asignar_equipos;
	}



	public Long getId_voluntario() {
		return id_voluntario;
	}



	public void setId_voluntario(Long id_voluntario) {
		this.id_voluntario = id_voluntario;
	}



	public Long getId_equipos() {
		return id_equipos;
	}



	public void setId_equipos(Long id_equipos) {
		this.id_equipos = id_equipos;
	}



	public Date getFecha_asinacionequi() {
		return fecha_asinacionequi;
	}



	public void setFecha_asinacionequi(Date fecha_asinacionequi) {
		this.fecha_asinacionequi = fecha_asinacionequi;
	}



	private static final long serialVersionUID = 1L;

}
