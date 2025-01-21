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
@Table(name="asignacion_proyecto")
public class Asignacion_proyectoActi implements Serializable {

	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_asignacionproyecto;
	@Column(name = "create_as")
    @Temporal(TemporalType.DATE)
    private Date createAs;

    @PrePersist
    public void prePersist() {
        createAs = new Date();
    }
    
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
    
    
}
