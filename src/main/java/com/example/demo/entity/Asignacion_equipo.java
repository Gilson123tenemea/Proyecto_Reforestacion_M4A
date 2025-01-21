package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="asignacion_equipos")
public class Asignacion_equipo implements Serializable {

	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_asig_eq;
	private String horas_trabajadas;
	@Column(name = "create_ae")
    @Temporal(TemporalType.DATE)
    private Date createAe;

    @PrePersist
    public void prePersist() {
        createAe = new Date();
    }
    
    private static final long serialVersionUID = -4518447769113877806L;

	public Long getId_asig_eq() {
		return id_asig_eq;
	}

	public void setId_asig_eq(Long id_asig_eq) {
		this.id_asig_eq = id_asig_eq;
	}

	public String getHoras_trabajadas() {
		return horas_trabajadas;
	}

	public void setHoras_trabajadas(String horas_trabajadas) {
		this.horas_trabajadas = horas_trabajadas;
	}

	public Date getCreateAe() {
		return createAe;
	}

	public void setCreateAe(Date createAe) {
		this.createAe = createAe;
	}
    
    
}