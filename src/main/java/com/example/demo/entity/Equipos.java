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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "equipo", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_voluntario","id_administrador","id_asignacionproyecto","id_equipos"})})
public class Equipos implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_equipos;
	
	private Long id_voluntario;
	private Long id_administrador;
	private Long id_asignacionproyecto;
	private int cantidad_equipo;
	private Boolean asistencia;
	private String nombre;
	
	private Date fecha;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_equipos")
	private List<Intervencion_Suelo> equipos ; 

	
	
	public Long getId_equipos() {
		return id_equipos;
	}


	public void setId_equipos(Long id_equipos) {
		this.id_equipos = id_equipos;
	}


	


	public Long getId_voluntario() {
		return id_voluntario;
	}


	public void setId_voluntario(Long id_voluntario) {
		this.id_voluntario = id_voluntario;
	}


	public Long getId_administrador() {
		return id_administrador;
	}


	public void setId_administrador(Long id_administrador) {
		this.id_administrador = id_administrador;
	}


	public Long getId_asignacionproyecto() {
		return id_asignacionproyecto;
	}


	public void setId_asignacionproyecto(Long id_asignacionproyecto) {
		this.id_asignacionproyecto = id_asignacionproyecto;
	}


	public int getCantidad_equipo() {
		return cantidad_equipo;
	}


	public void setCantidad_equipo(int cantidad_equipo) {
		this.cantidad_equipo = cantidad_equipo;
	}


	public Boolean getAsistencia() {
		return asistencia;
	}


	public void setAsistencia(Boolean asistencia) {
		this.asistencia = asistencia;
	}
	


	public List<Intervencion_Suelo> getEquipos() {
		return equipos;
	}


	public void setEquipos(List<Intervencion_Suelo> equipos) {
		this.equipos = equipos;
	}



	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}




	private static final long serialVersionUID = 1L;

}
