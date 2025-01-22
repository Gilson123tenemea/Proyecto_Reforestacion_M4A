package com.example.demo.entity;

import java.io.Serializable;
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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Ubicacion",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuarios","id_ubicacion"})})
public class Ubicacion implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_ubicacion;
	
	private String Provincia;
	private String Canton;
	private String Ciudad;
	
	//relacion con proyecto
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name ="id_ubicacion")
	private List<Proyecto> proyecto ; 

	
	private Long id_usuarios;
	
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


	public Long getId_usuarios() {
		return id_usuarios;
	}

	public void setId_usuarios(Long id_usuarios) {
		this.id_usuarios = id_usuarios;
	}
	
	public List<Proyecto> getProyecto() {
		return proyecto;
	}

	public void setProyecto(List<Proyecto> proyecto) {
		this.proyecto = proyecto;
	}

	private static final long serialVersionUID = 1L;

}
