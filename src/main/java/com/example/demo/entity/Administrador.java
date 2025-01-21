package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrador")
public class Administrador implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_administrador;

	private Long cedula;
	private Long id_super_administrador;

	public Long getId_administrador() {
		return id_administrador;
	}

	public void setId_administrador(Long id_administrador) {
		this.id_administrador = id_administrador;
	}

	public Long getCedula() {
		return cedula;
	}

	public void setCedula(Long cedula) {
		this.cedula = cedula;
	}

	public Long getId_super_administrador() {
		return id_super_administrador;
	}

	public void setId_super_administrador(Long id_super_administrador) {
		this.id_super_administrador = id_super_administrador;
	}

	private static final long serialVersionUID = 1L;

}
