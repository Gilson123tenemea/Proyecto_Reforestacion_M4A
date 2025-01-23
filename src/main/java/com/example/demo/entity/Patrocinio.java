package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "patrocinio",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_proyecto","Id_patrocinador","Id_patrocina"})})
public class Patrocinio implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id_patrocina;
	@Temporal(TemporalType.DATE)
	private Date fechainicio;
	@Temporal(TemporalType.DATE)
	private Date fechafin;
	private String tipo_patrocinio;
	private Double cantiad_estimada;
	// llaves FK
	
	private Long id_proyecto;
	private Long Id_patrocinador;
	
	
	public Long getId_patrocina() {
		return Id_patrocina;
	}

	public void setId_patrocina(Long id_patrocina) {
		Id_patrocina = id_patrocina;
	}

	public Date getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	public Date getFechafin() {
		return fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public String getTipo_patrocinio() {
		return tipo_patrocinio;
	}

	public void setTipo_patrocinio(String tipo_patrocinio) {
		this.tipo_patrocinio = tipo_patrocinio;
	}

	public Double getCantiad_estimada() {
		return cantiad_estimada;
	}

	public void setCantiad_estimada(Double cantiad_estimada) {
		this.cantiad_estimada = cantiad_estimada;
	}

	public Long getId_proyecto() {
		return id_proyecto;
	}

	public void setId_proyecto(Long id_proyecto) {
		this.id_proyecto = id_proyecto;
	}

	public Long getId_patrocinador() {
		return Id_patrocinador;
	}

	public void setId_patrocinador(Long id_patrocinador) {
		Id_patrocinador = id_patrocinador;
	}

	private static final long serialVersionUID = 1L;

}
