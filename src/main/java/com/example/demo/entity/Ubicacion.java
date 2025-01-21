package com.example.demo.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ubicacion")
public class Ubicacion implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_ubicacion;
	
	private String Provincia;
	private String Canton;
	private String Ciudad;
	
	public Long getId_ubicacion() {
		return id_ubicacion;
	}

	public void setId_ubicacion(Long id_ubicacion) {
		this.id_ubicacion = id_ubicacion;
	}

	public String getProvincia() {
		return Provincia;
	}

	public void setProvincia(String provincia) {
		Provincia = provincia;
	}

	public String getCanton() {
		return Canton;
	}

	public void setCanton(String canton) {
		Canton = canton;
	}

	public String getCiudad() {
		return Ciudad;
	}

	public void setCiudad(String ciudad) {
		Ciudad = ciudad;
	}

	private static final long serialVersionUID = 1L;

}
