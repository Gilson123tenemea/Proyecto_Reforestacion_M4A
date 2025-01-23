package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "equipos",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_administrador","id_equipo"})})
public class Equipos implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_equipo;

	//relacion con administrador
	private Long id_administrador;
	private int numeroequipo;
	
	@Temporal(TemporalType.DATE)
	private Date fechaInicio;
	@Temporal(TemporalType.DATE)
	private Date fechaFin;
	
	//relacion con asignacion equipo
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_equipo")
	private List<Asignacion_equipo> asinacion_equipo ; 
	
	
	//relacion con intervencion suelo
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_equipo")
	private List<Intervencion_Suelo> intervencion_suelo  ; 
		

	public Long getId_equipo() {
		return id_equipo;
	}

	public void setId_equipo(Long id_equipo) {
		this.id_equipo = id_equipo;
	}

	public Long getId_administrador() {
		return id_administrador;
	}

	public void setId_administrador(Long id_administrador) {
		this.id_administrador = id_administrador;
	}

	public int getNumeroequipo() {
		return numeroequipo;
	}

	public void setNumeroequipo(int numeroequipo) {
		this.numeroequipo = numeroequipo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	public List<Asignacion_equipo> getAsinacion_equipo() {
		return asinacion_equipo;
	}

	public void setAsinacion_equipo(List<Asignacion_equipo> asinacion_equipo) {
		this.asinacion_equipo = asinacion_equipo;
	}
	
	public List<Intervencion_Suelo> getIntervencion_suelo() {
		return intervencion_suelo;
	}

	public void setIntervencion_suelo(List<Intervencion_Suelo> intervencion_suelo) {
		this.intervencion_suelo = intervencion_suelo;
	}

	private static final long serialVersionUID = 1L;

}
