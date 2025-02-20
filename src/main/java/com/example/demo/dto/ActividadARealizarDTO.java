package com.example.demo.dto;

public class ActividadARealizarDTO {

    private Long idVoluntario;
    private Long idTipoActividad;
    private Integer cantidadRealizada;
    private String descripcion;
    private boolean validacionAdmin;
    private boolean validacionVoluntario;
    private String foto; // En formato base64 si es necesario

    // Getters y Setters
    public Long getIdVoluntario() {
        return idVoluntario;
    }

    public void setIdVoluntario(Long idVoluntario) {
        this.idVoluntario = idVoluntario;
    }

    public Long getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(Long idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    public Integer getCantidadRealizada() {
        return cantidadRealizada;
    }

    public void setCantidadRealizada(Integer cantidadRealizada) {
        this.cantidadRealizada = cantidadRealizada;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isValidacionAdmin() {
        return validacionAdmin;
    }

    public void setValidacionAdmin(boolean validacionAdmin) {
        this.validacionAdmin = validacionAdmin;
    }

    public boolean isValidacionVoluntario() {
        return validacionVoluntario;
    }

    public void setValidacionVoluntario(boolean validacionVoluntario) {
        this.validacionVoluntario = validacionVoluntario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
