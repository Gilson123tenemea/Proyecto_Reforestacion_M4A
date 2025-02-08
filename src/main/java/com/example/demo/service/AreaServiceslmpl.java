package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IAreaDao;
import com.example.demo.dao.IProyectoDao;
import com.example.demo.entity.Area;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.Suelo;

@Service
public class AreaServiceslmpl implements IAreaServices{
	
	@Autowired
	private IAreaDao areadao;
	@Autowired
	private IProyectoDao proyectodao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Area> findAll() {
		return areadao.findAll();
	}

	@Transactional
	@Override
	public void save(Area area) {
		areadao.save(area);
	}

	@Transactional(readOnly = true)
	@Override
	public Area findOne(Long id) {
		return areadao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		areadao.delete(id);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Area> listarAreas() {
		return areadao.findAll();
	}

	@Override
	public String findProyectoNameByAreaId(Long idArea) {
		 Area area = areadao.findOne(idArea);
		    if (area != null) {
		        Long idProyecto = area.getId_proyecto(); // Obtener el ID del proyecto
		        Proyecto proyecto = proyectodao.findOne(idProyecto); // Buscar el proyecto por ID
		        return proyecto != null ? proyecto.getNombre() : null; // Retornar el nombre del proyecto
		    }
		    return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> findByProyectoIdAdministrador(Long adminId) {
		return areadao.findByProyectoIdAdministrador(adminId);
	}

}
