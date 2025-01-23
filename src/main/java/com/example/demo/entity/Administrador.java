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
@Table(name = "administrador", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuarios","id_super_administrador","id_administrador"})})
public class Administrador implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_administrador;

	//relacion usuarios
	private Long id_usuarios;
	private int actividades_gestionadas;
	private Long id_super_administrador;

	//relacion con equipos
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_administrador")
	private List<Equipos> equipos  ; 
	
	
	//relacion con proyecto
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_administrador")
	private List<Proyecto> proyecto ; 

	
	//relacion con monitoreo
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_administrador")
	private List<Monitoreo> monitoreo ; 

	
	public Long getId_administrador() {
		return id_administrador;
	}

	public void setId_administrador(Long id_administrador) {
		this.id_administrador = id_administrador;
	}

	public Long getId_usuarios() {
		return id_usuarios;
	}

	public void setId_usuarios(Long id_usuarios) {
		this.id_usuarios = id_usuarios;
	}

	public Long getId_super_administrador() {
		return id_super_administrador;
	}

	public void setId_super_administrador(Long id_super_administrador) {
		this.id_super_administrador = id_super_administrador;
	}

	public List<Equipos> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<Equipos> equipos) {
		this.equipos = equipos;
	}

	public List<Proyecto> getProyecto() {
		return proyecto;
	}

	public void setProyecto(List<Proyecto> proyecto) {
		this.proyecto = proyecto;
	}
	public List<Monitoreo> getMonitoreo() {
		return monitoreo;
	}

	public void setMonitoreo(List<Monitoreo> monitoreo) {
		this.monitoreo = monitoreo;
	}
	
	public int getActividades_gestionadas() {
		return actividades_gestionadas;
	}

	public void setActividades_gestionadas(int actividades_gestionadas) {
		this.actividades_gestionadas = actividades_gestionadas;
	}



	private static final long serialVersionUID = 1L;

}
