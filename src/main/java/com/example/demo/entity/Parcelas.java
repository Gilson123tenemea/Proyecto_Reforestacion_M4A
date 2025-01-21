package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table

public class Parcelas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_parcelas;
	private Long id_plantas;
	private Long id_suelo;
	private double altura;
	private double ancho;
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
	public double getAltura() {
		return altura;
	}
	public void setAltura(double altura) {
		this.altura = altura;
	}
	public double getAncho() {
		return ancho;
	}
	public void setAncho(double ancho) {
		this.ancho = ancho;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
