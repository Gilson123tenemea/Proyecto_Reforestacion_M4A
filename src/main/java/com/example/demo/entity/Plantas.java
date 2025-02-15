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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "plantas", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_especie", "id_plantas"})})
public class Plantas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_plantas;

    private Long id_especie;

    @NotNull(message = "El nombre científico no puede ser nulo")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre científico debe contener solo letras y espacios")
    private String nombre_cientifico;

    @NotNull(message = "El clima no puede ser nulo")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El clima debe contener solo letras y espacios")
    private String clima;

    @NotNull(message = "El nombre común no puede ser nulo")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre común debe contener solo letras y espacios")
    private String nombre_comun;

    @NotNull(message = "El tamaño no puede ser nulo")
    @Positive(message = "El tamaño debe ser un número positivo")
    private Double tamaño;

    @NotNull(message = "El color de las hojas no puede ser nulo")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El color de las hojas debe contener solo letras y espacios")
    private String color_hojas;
    
    // Relación con parcelas
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_plantas")
    private List<Parcelas> parcelas;

    // Relación con monitoreo
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_plantas")
    private List<Monitoreo> monitoreo;

    public Long getId_plantas() {
        return id_plantas;
    }

    public void setId_plantas(Long id_plantas) {
        this.id_plantas = id_plantas;
    }

    public Long getId_especie() {
        return id_especie;
    }

    public void setId_especie(Long id_especie) {
        this.id_especie = id_especie;
    }

    public String getNombre_cientifico() {
        return nombre_cientifico;
    }

    public void setNombre_cientifico(String nombre_cientifico) {
        this.nombre_cientifico = nombre_cientifico;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getNombre_comun() {
        return nombre_comun;
    }

    public void setNombre_comun(String nombre_comun) {
        this.nombre_comun = nombre_comun;
    }

    public Double getTamaño() {
        return tamaño;
    }

    public void setTamaño(Double tamaño) {
        this.tamaño = tamaño;
    }

    public String getColor_hojas() {
        return color_hojas;
    }

    public void setColor_hojas(String color_hojas) {
        this.color_hojas = color_hojas;
    }

    public List<Parcelas> getParcelas() {
        return parcelas;
    }

    public void setParcelas(List<Parcelas> parcelas) {
        this.parcelas = parcelas;
    }

    public List<Monitoreo> getMonitoreo() {
        return monitoreo;
    }

    public void setMonitoreo(List<Monitoreo> monitoreo) {
        this.monitoreo = monitoreo;
    }

    private static final long serialVersionUID = 1L;
}
