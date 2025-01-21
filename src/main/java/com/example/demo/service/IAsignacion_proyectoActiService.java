package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Asignacion_proyectoActi;
@Service
public interface IAsignacion_proyectoActiService {

    public List<Asignacion_proyectoActi> findAll();
	
	public void save (Asignacion_proyectoActi aproyecto);
	
	public Asignacion_proyectoActi findOne (Long id_asignacionproyecto);

	public void delete(Long id_asignacionproyecto);
}
