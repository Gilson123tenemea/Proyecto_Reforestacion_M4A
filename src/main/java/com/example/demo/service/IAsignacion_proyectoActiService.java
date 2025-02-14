package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Asignacion_proyectoActi;

@Service
public interface IAsignacion_proyectoActiService {

    public List<Asignacion_proyectoActi> findAll();
	public void save (Asignacion_proyectoActi asignacion_proyecto);
	public Asignacion_proyectoActi findOne (Long id);
	public void delete(Long id);
	List<Asignacion_proyectoActi> findByAdministradorId(Long idAdministrador); 
	long countByProyectoId(Long idProyecto);
    List<Asignacion_proyectoActi> findByProyectoId(Long idProyecto);

}
