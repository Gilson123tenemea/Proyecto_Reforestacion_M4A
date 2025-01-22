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
@Table(name = "Monitoreo",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_administrador","id_actividades","id_proyecto","id_monitoreo"})})

public class Monitoreo implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_monitoreo;
	
	private Long id_administrador;
	private Long id_actividades;
	private Long id_proyecto;
	
	private Double altura_plantas;
	private String duracion_monitoreo;
	private String problemas_idetificados;
	private String observaciones;
	private String nota_administrador;
	private Date fecha;
	private String imagen;
	
	public Long getId_monitoreo() {
		return id_monitoreo;
	}
	public void setId_monitoreo(Long id_monitoreo) {
		this.id_monitoreo = id_monitoreo;
	}
	public Long getId_administrador() {
		return id_administrador;
	}
	public void setId_administrador(Long id_administrador) {
		this.id_administrador = id_administrador;
	}
	public Long getId_actividades() {
		return id_actividades;
	}
	public void setId_actividades(Long id_actividades) {
		this.id_actividades = id_actividades;
	}
	public double getAltura_plantas() {
		return altura_plantas;
	}
	public void setAltura_plantas(double altura_plantas) {
		this.altura_plantas = altura_plantas;
	}
	
	public String getDuracion_monitoreo() {
		return duracion_monitoreo;
	}
	public void setDuracion_monitoreo(String duracion_monitoreo) {
		this.duracion_monitoreo = duracion_monitoreo;
	}
	public String getProblemas_idetificados() {
		return problemas_idetificados;
	}
	public void setProblemas_idetificados(String problemas_idetificados) {
		this.problemas_idetificados = problemas_idetificados;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getNota_administrador() {
		return nota_administrador;
	}
	public void setNota_administrador(String nota_administrador) {
		this.nota_administrador = nota_administrador;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public Long getId_proyecto() {
		return id_proyecto;
	}
	public void setId_proyecto(Long id_proyecto) {
		this.id_proyecto = id_proyecto;
	}

	private static final long serialVersionUID = 1L;
	


}
