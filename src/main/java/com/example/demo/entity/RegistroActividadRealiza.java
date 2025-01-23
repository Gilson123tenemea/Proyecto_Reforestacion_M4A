package com.example.demo.entity;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "registroactividadesrealiza",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_voluntario","id_intervencionSuelo","id_registroactividadrealizada"})})
public class RegistroActividadRealiza implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_registroactividadrealizada;
	private Long id_voluntario;
	private Long id_intervencionSuelo;
	
	private int cantidad_realizada;
	private String descripcion;
	private String foto;
	
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

	public Long getId_intervencionSuelo() {
		return id_intervencionSuelo;
	}

	public void setId_intervencionSuelo(Long id_intervencionSuelo) {
		this.id_intervencionSuelo = id_intervencionSuelo;
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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	private static final long serialVersionUID = 1L;

}
