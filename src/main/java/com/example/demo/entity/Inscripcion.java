package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Inscripcion",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_voluntario","id_actividades","id_proyecto","id_inscripcion"})})
public class Inscripcion implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_inscripcion;
	
	private Long id_voluntario;
	private Long id_actividades;
	private Long id_proyecto;
	
	@Column(name = "create_as")
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	 @PrePersist
	    public void prePersist() {
		 fecha = new Date();
	    }
	
	public Long getId_inscripcion() {
		return id_inscripcion;
	}

	public void setId_inscripcion(Long id_inscripcion) {
		this.id_inscripcion = id_inscripcion;
	}

	public Long getId_voluntario() {
		return id_voluntario;
	}

	public void setId_voluntario(Long id_voluntario) {
		this.id_voluntario = id_voluntario;
	}

	public Long getId_actividades() {
		return id_actividades;
	}

	public void setId_actividades(Long id_actividades) {
		this.id_actividades = id_actividades;
	}

	public Long getId_proyecto() {
		return id_proyecto;
	}

	public void setId_proyecto(Long id_proyecto) {
		this.id_proyecto = id_proyecto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	private static final long serialVersionUID = 1L;

}
