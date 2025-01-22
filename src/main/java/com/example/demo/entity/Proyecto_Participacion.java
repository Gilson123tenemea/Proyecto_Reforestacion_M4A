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

@Entity
@Table(name = "proyectoparticipacion")
public class Proyecto_Participacion implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_proyecto_participacion;
	private String nomnbre;
	private String actividades;
	private Date fechaparticipo;
	
	//relacion con super voluntarios
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_proyecto_participacion")
	private List<Voluntarios> voluntarios ; 
		

	public Long getId_proyecto_participacion() {
		return id_proyecto_participacion;
	}

	public void setId_proyecto_participacion(Long id_proyecto_participacion) {
		this.id_proyecto_participacion = id_proyecto_participacion;
	}

	public String getNomnbre() {
		return nomnbre;
	}

	public void setNomnbre(String nomnbre) {
		this.nomnbre = nomnbre;
	}

	public String getActividades() {
		return actividades;
	}

	public void setActividades(String actividades) {
		this.actividades = actividades;
	}

	public Date getFechaparticipo() {
		return fechaparticipo;
	}

	public void setFechaparticipo(Date fechaparticipo) {
		this.fechaparticipo = fechaparticipo;
	}

	public List<Voluntarios> getVoluntarios() {
		return voluntarios;
	}

	public void setVoluntarios(List<Voluntarios> voluntarios) {
		this.voluntarios = voluntarios;
	}


	private static final long serialVersionUID = 1L;

}
