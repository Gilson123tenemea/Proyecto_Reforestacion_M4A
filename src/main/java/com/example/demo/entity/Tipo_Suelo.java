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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "tipo_suelos")
public class Tipo_Suelo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tiposuelo;

    @NotEmpty(message = "El nombre del suelo no puede estar vacío.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre del suelo solo puede contener letras, espacios y tildes.")
    private String nombre_suelo;


    
    private String descripcion;

    @NotEmpty(message = "El color no puede estar vacío.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El color solo puede contener letras, espacios y tildes.")
    private String color;

    @NotEmpty(message = "La textura no puede estar vacía.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "La textura del suelo solo puede contener letras, espacios y tildes.")
    private String textura;


    @NotNull(message = "La densidad no puede ser nula.")
    @Pattern(regexp = "^\\d+(\\.\\d+)?\\s?-\\s?\\d+(\\.\\d+)?$", 
             message = "La densidad debe estar en formato 'x.x - y.y'.")
    private String densidad;

    @NotNull(message = "El pH no puede ser nulo.")
    @Pattern(regexp = "^\\d+(\\.\\d+)?\\s?-\\s?\\d+(\\.\\d+)?$", 
             message = "El pH debe estar en formato 'x.x - y.y'.")
    private String ph;

   
    private String composicion;

    @NotEmpty(message = "La fertilidad no puede estar vacía.")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "La fertilidad del suelo solo puede contener letras, espacios y tildes.")
    private String fertilidad;


    
    private String uso_del_suelo;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tiposuelo")
    private List<Suelo> suelo;

    private static final long serialVersionUID = 1L;

    public Long getId_tiposuelo() {
        return id_tiposuelo;
    }

    public void setId_tiposuelo(Long id_tiposuelo) {
        this.id_tiposuelo = id_tiposuelo;
    }

    public String getNombre_suelo() {
        return nombre_suelo;
    }

    public void setNombre_suelo(String nombre_suelo) {
        this.nombre_suelo = nombre_suelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextura() {
        return textura;
    }

    public void setTextura(String textura) {
        this.textura = textura;
    }

    public String getDensidad() {
        return densidad;
    }

    public void setDensidad(String densidad) {
        this.densidad = densidad;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getComposicion() {
        return composicion;
    }

    public void setComposicion(String composicion) {
        this.composicion = composicion;
    }

    public String getFertilidad() {
        return fertilidad;
    }

    public void setFertilidad(String fertilidad) {
        this.fertilidad = fertilidad;
    }

    public String getUso_del_suelo() {
        return uso_del_suelo;
    }

    public void setUso_del_suelo(String uso_del_suelo) {
        this.uso_del_suelo = uso_del_suelo;
    }

    public List<Suelo> getSuelo() {
        return suelo;
    }

    public void setSuelo(List<Suelo> suelo) {
        this.suelo = suelo;
    }

}
