package com.example.demo.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "asiganacion_actividades",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_tipoActividades","id_inscripcion","asignacion_actividades"})})
public class asignacion_actividad implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long asignacion_actividades;
	private String voluntarios_array;
	private Long id_tipoActividades;
	private Long id_inscripcion;
	
	
	@Column(name = "create_as")
    @Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAs;
	
	 @PrePersist
	    public void prePersist() {
	        createAs = new Date();
	    }
	 
	 
	 @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	 @JoinColumn(name ="asignacion_actividades")
	 private List<Intervencion_Suelo> intervencion_suelo   ; 	
	
	 public Long getId_inscripcionactividades() {
		return asignacion_actividades;
	}

	public void setId_inscripcionactividades(Long id_inscripcionactividades) {
		this.asignacion_actividades = id_inscripcionactividades;
	}

	public Long getId_tipoActividades() {
		return id_tipoActividades;
	}

	public void setId_tipoActividades(Long id_tipoActividades) {
		this.id_tipoActividades = id_tipoActividades;
	}


	public Date getCreateAs() {
		return createAs;
	}

	public void setCreateAs(Date createAs) {
		this.createAs = createAs;
	}
	
	public Long getAsignacion_actividades() {
		return asignacion_actividades;
	}

	public void setAsignacion_actividades(Long asignacion_actividades) {
		this.asignacion_actividades = asignacion_actividades;
	}

	public String getVoluntarios_array() {
		return voluntarios_array;
	}

	public void setVoluntarios_array(String voluntarios_array) {
		this.voluntarios_array = voluntarios_array;
	}

	public Long getId_inscripcion() {
		return id_inscripcion;
	}

	public void setId_inscripcion(Long id_inscripcion) {
		this.id_inscripcion = id_inscripcion;
	}
	
	
	public List<Intervencion_Suelo> getIntervencion_suelo() {
		return intervencion_suelo;
	}

	public void setIntervencion_suelo(List<Intervencion_Suelo> intervencion_suelo) {
		this.intervencion_suelo = intervencion_suelo;
	}


	static final long serialVersionUID = 1L;

}
