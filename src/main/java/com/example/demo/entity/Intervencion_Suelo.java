package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "Intervencion_Suelo",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_parcelas","id_equipos","id_intervencion_suelo"})})
public class Intervencion_Suelo implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_intervencion_suelo;
	
	private Long id_parcelas;
	private Long id_equipos;	

	
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd") 

	private Date fecha_asignacion;
	
	private String hora_asignacion;
	private String observaciones;
	
	
	//relacion con RegistroActividadSuelo
	 @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	 @JoinColumn(name ="id_asignacionActividades")
	 private List<RegistroActividadRealiza> registroactividadesrealizada   ; 	
	

	public Long getId_intervencion_suelo() {
		return id_intervencion_suelo;
	}

	public void setId_intervencion_suelo(Long id_intervencion_suelo) {
		this.id_intervencion_suelo = id_intervencion_suelo;
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
	

	public Long getId_parcelas() {
		return id_parcelas;
	}

	public void setId_parcelas(Long id_parcelas) {
		this.id_parcelas = id_parcelas;
	}

	public Long getId_equipos() {
		return id_equipos;
	}

	public void setId_equipos(Long id_equipos) {
		this.id_equipos = id_equipos;
	}


	private static final long serialVersionUID = 1L;
	

}
