package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name="asignaciones",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_asignacionproyecto","id_actividades","id_proyecto"})})
public class Asignacion_proyectoActi implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_asignacionproyecto;
	
	@Column(name = "create_as")
    @Temporal(TemporalType.DATE)
    private Date createAs;
	
	private Long id_proyecto;
	
	private Long id_tipoActividades;
	private Boolean estado;
	@NotEmpty(message = "La meta deseada no puede estar vacía.")
	private String meta_deseada;
	private Double porcentajeActividad;


    @PrePersist
    public void prePersist() {
        createAs = new Date();
    }
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_asignacionproyecto")
	private List<Equipos> equipos ; 

    
    private static final long serialVersionUID = 1L;

	public Long getId_asignacionproyecto() {
		return id_asignacionproyecto;
	}

	public void setId_asignacionproyecto(Long id_asignacionproyecto) {
		this.id_asignacionproyecto = id_asignacionproyecto;
	}

	public Date getCreateAs() {
		return createAs;
	}

	public void setCreateAs(Date createAs) {
		this.createAs = createAs;
	}

	public Long getId_proyecto() {
		return id_proyecto;
	}

	public void setId_proyecto(Long id_proyecto) {
		this.id_proyecto = id_proyecto;
	}

	public Long getId_tipoActividades() {
		return id_tipoActividades;
	}

	public void setId_tipoActividades(Long id_tipoActividades) {
		this.id_tipoActividades = id_tipoActividades;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}


	public String getMeta_deseada() {
		return meta_deseada;
	}

	public void setMeta_deseada(String meta_deseada) {
		this.meta_deseada = meta_deseada;
	}

	public List<Equipos> getEquipos() {
		return equipos;
	}

	public void setEquipos(List<Equipos> equipos) {
		this.equipos = equipos;
	}

	public Double getPorcentajeActividad() {
		return porcentajeActividad;
	}

	public void setPorcentajeActividad(Double porcentajeActividad) {
		this.porcentajeActividad = porcentajeActividad;
	}
	
	

}
