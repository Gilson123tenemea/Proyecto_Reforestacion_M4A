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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "inscripcion_actividades",uniqueConstraints = {@UniqueConstraint(columnNames = {"id_voluntario","id_tipoActividades","id_inscripcionactividades"})})
public class Inscripcion_actividades implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_inscripcionactividades;
	
	private Long id_voluntario;
	private Long id_tipoActividades;
	
	@Column(name = "create_as")
    @Temporal(TemporalType.DATE)
    private Date createAs;
	
	 @PrePersist
	    public void prePersist() {
	        createAs = new Date();
	    }
	
	 public Long getId_inscripcionactividades() {
		return id_inscripcionactividades;
	}

	public void setId_inscripcionactividades(Long id_inscripcionactividades) {
		this.id_inscripcionactividades = id_inscripcionactividades;
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


	public Date getCreateAs() {
		return createAs;
	}

	public void setCreateAs(Date createAs) {
		this.createAs = createAs;
	}

	static final long serialVersionUID = 1L;

}
