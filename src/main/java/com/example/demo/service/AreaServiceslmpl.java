package com.example.demo.service;

import java.util.Date;
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
		        Long idProyecto = area.getId_proyecto(); 
		        Proyecto proyecto = proyectodao.findOne(idProyecto); 
		        return proyecto != null ? proyecto.getNombre() : null; 
		    }
		    return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Area> findByProyectoIdAdministrador(Long adminId) {
		return areadao.findByProyectoIdAdministrador(adminId);
	}
	
	@Override
	public long countParcelasByAreaId(Long idArea) {
	    return areadao.countParcelasByAreaId(idArea);
	}
	
	@Override
	public Object findProyecto(Long idArea) {
	    Area area = areadao.findOne(idArea);
	    if (area != null) {
	        Long idProyecto = area.getId_proyecto(); 
	        Proyecto proyecto = proyectodao.findOne(idProyecto); 
	        if (proyecto != null) {
	            return new Object() {
	                public Long id_proyecto = proyecto.getId_proyecto();
	                public String nombre = proyecto.getNombre();
	                public Date fecha_inicio = proyecto.getFecha_inicio();
	                public Date fecha_fin = proyecto.getFecha_fin();
	                public String estado = proyecto.getEstado();
	                public int voluntariosmax =proyecto.getVoluntariosmax();
	                public Long id_parroquia = proyecto.getId_parroquia();
	                public byte[]  imagen=proyecto.getImagen();
	            };
	        }
	    }
	    return null;
	}
	@Override
	public String findAreaName(Long idArea) {
	    Area area = areadao.findOne(idArea);
	    return area != null ? area.getNombre() : null;
	}

}
