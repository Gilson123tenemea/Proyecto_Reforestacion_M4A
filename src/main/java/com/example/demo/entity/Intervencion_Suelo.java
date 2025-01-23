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
@Table(name = "Intervencion_Suelo",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_voluntario","id_tipoActividades","id_suelo","id_asignacionActividades"})})
public class Intervencion_Suelo implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_asignacionActividades;
	
	private Long id_tipoActividades;
	private Long id_suelo;
	private Long id_voluntario;
	
	@Temporal(TemporalType.DATE)
	private Date fecha_asignacion;
	
	private String hora_asignacion;
	private String observaciones;
	
	
	//relacion con RegistroActividadSuelo
	 @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	 @JoinColumn(name ="id_asignacionActividades")
	 private List<RegistroActividadRealiza> registroactividadesrealizada   ; 	
	
	public Long getId_asignacionActividades() {
		return id_asignacionActividades;
	}

	public void setId_asignacionActividades(Long id_asignacionActividades) {
		this.id_asignacionActividades = id_asignacionActividades;
	}

	public Long getId_tipoActividades() {
		return id_tipoActividades;
	}

	public void setId_tipoActividades(Long id_tipoActividades) {
		this.id_tipoActividades = id_tipoActividades;
	}

	public Long getId_suelo() {
		return id_suelo;
	}

	public void setId_suelo(Long id_suelo) {
		this.id_suelo = id_suelo;
	}


	public Long getId_voluntario() {
		return id_voluntario;
	}

	public void setId_voluntario(Long id_voluntario) {
		this.id_voluntario = id_voluntario;
	}

	public Date getFecha_asignacion() {
		return fecha_asignacion;
	}

	public void setFecha_asignacion(Date fecha_asignacion) {
		this.fecha_asignacion = fecha_asignacion;
	}

	public String getHora_asignacion() {
		return hora_asignacion;
	}

	public void setHora_asignacion(String hora_asignacion) {
		this.hora_asignacion = hora_asignacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public List<RegistroActividadRealiza> getRegistroactividadesrealizada() {
		return registroactividadesrealizada;
	}

	public void setRegistroactividadesrealizada(List<RegistroActividadRealiza> registroactividadesrealizada) {
		this.registroactividadesrealizada = registroactividadesrealizada;
	}


	private static final long serialVersionUID = 1L;
	

}
