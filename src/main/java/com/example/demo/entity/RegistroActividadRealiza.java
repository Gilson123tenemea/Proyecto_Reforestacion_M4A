package com.example.demo.entity;
import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "registroactividadesrealiza",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_voluntario","id_intervencion_suelo","id_registroactividadrealizada"})})
public class RegistroActividadRealiza implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_registroactividadrealizada;
	private Long id_voluntario;
	private Long id_intervencion_suelo;
	
	private int cantidad_realizada;
	private String descripcion;
	
	
	@Lob
	@Column(name = "foto", columnDefinition = "LONGBLOB")
	private byte[] foto;



	private Boolean validacion_admin_tareaRealizada;
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

	

	public Long getId_intervencion_suelo() {
		return id_intervencion_suelo;
	}

	public void setId_intervencion_suelo(Long id_intervencion_suelo) {
		this.id_intervencion_suelo = id_intervencion_suelo;
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

