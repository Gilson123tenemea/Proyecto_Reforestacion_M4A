package com.example.demo.dto;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

public class ProyectoDTO {
    private Long id;
    private String nombre;
	private Date fecha_inicio;
	private Date fecha_fin;
	private int voluntariosmax;
    // Constructor
		public ProyectoDTO(Long id, String nombre, Date fecha_inicio, Date fecha_fin, int voluntariosmax) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
		this.voluntariosmax = voluntariosmax;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	public Date getFecha_fin() {
		return fecha_fin;
	}
	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}
	public int getVoluntariosmax() {
		return voluntariosmax;
	}
	public void setVoluntariosmax(int voluntariosmax) {
		this.voluntariosmax = voluntariosmax;
	}
   

}
