package com.example.demo.entity;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "registroactividadesrealiza",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_voluntario","id_tipoActividades","id_registroactividadrealizada"})})
public class RegistroActividadRealiza implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_registroactividadrealizada;
	private Long id_voluntario;
	private Long id_tipoActividades;
	
	private int cantidad_realizada;
	private String descripcion;
	
	
	@Lob
	@Column(name = "foto", columnDefinition = "LONGBLOB")
	private byte[] foto;

	private Boolean validacion_admin_tareaRealizada;
	@Column(name = "validacion_voluntario_tarea_realizada")
	private Boolean validacion_voluntario_tareaRealizada;
	
	public Long getId_registroactividadrealizada() {
		return id_registroactividadrealizada;
	}

	public void setId_registroactividadrealizada(Long id_registroactividadrealizada) {
		this.id_registroactividadrealizada = id_registroactividadrealizada;
	}

	public Long getId_voluntario() {
		return id_voluntario;
	}

	public void setId_voluntario(Long id_voluntario) {
		this.id_voluntario = id_voluntario;
	}


	public Long getId_tipoActividades() {
		return id_tipoActividades;
	}

	public void setId_tipoActividades(Long id_tipoActividades) {
		this.id_tipoActividades = id_tipoActividades;
	}

	public int getCantidad_realizada() {
		return cantidad_realizada;
	}

	public void setCantidad_realizada(int cantidad_realizada) {
		this.cantidad_realizada = cantidad_realizada;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getValidacion_admin_tareaRealizada() {
		return validacion_admin_tareaRealizada;
	}

	public void setValidacion_admin_tareaRealizada(Boolean validacion_admin_tareaRealizada) {
		this.validacion_admin_tareaRealizada = validacion_admin_tareaRealizada;
	}

	public Boolean getValidacion_voluntario_tareaRealizada() {
		return validacion_voluntario_tareaRealizada;
	}

	public void setValidacion_voluntario_tareaRealizada(Boolean validacion_voluntario_tareaRealizada) {
		this.validacion_voluntario_tareaRealizada = validacion_voluntario_tareaRealizada;
	}



	private static final long serialVersionUID = 1L;

		
	}

