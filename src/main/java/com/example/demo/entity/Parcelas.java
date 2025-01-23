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
@Table(name="parcelas",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_suelo","id_plantas","id_area","id_parcelas"})})

public class Parcelas implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_parcelas;
	private Long id_plantas;
	private Long id_area;
	private Long id_suelo;
	private double largo;
	private double ancho;
	private double x;
	private double y;
	
	//relacion con monitoreo
  	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  	@JoinColumn(name ="id_parcelas")
  	private List<Monitoreo> monitoreo  ; 
	
	public Long getId_parcelas() {
		return id_parcelas;
	}
	public void setId_parcelas(Long id_parcelas) {
		this.id_parcelas = id_parcelas;
	}
	public Long getId_plantas() {
		return id_plantas;
	}
	public void setId_plantas(Long id_plantas) {
		this.id_plantas = id_plantas;
	}
	public Long getId_suelo() {
		return id_suelo;
	}
	public void setId_suelo(Long id_suelo) {
		this.id_suelo = id_suelo;
	}
	
	public double getLargo() {
		return largo;
	}
	public void setLargo(double largo) {
		this.largo = largo;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getAncho() {
		return ancho;
	}
	public void setAncho(double ancho) {
		this.ancho = ancho;
	}
	
	public Long getId_area() {
		return id_area;
	}
	public void setId_area(Long id_area) {
		this.id_area = id_area;
	}
	public List<Monitoreo> getMonitoreo() {
		return monitoreo;
	}
	public void setMonitoreo(List<Monitoreo> monitoreo) {
		this.monitoreo = monitoreo;
	}

	
}
