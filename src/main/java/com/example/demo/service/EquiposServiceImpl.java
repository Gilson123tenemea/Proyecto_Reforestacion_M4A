package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IEquiposDao;
import com.example.demo.dao.IProyectoDao;
import com.example.demo.entity.Asignacion_proyectoActi;
import com.example.demo.entity.Equipos;
import com.example.demo.entity.Especie;
import com.example.demo.entity.Plantas;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.Tipo_Actividades;
import com.example.demo.entity.Usuarios;
import com.example.demo.entity.Voluntarios;

@Service
public class EquiposServiceImpl implements IEquiposService {

	@Autowired
	private IEquiposDao equiposdao;
	
	@Autowired
	private IProyectoDao proyectodao;
	
	public List<Object[]> obtenerActividadesPorHacer(Long voluntarioId) {
        return equiposdao.findActividadesPorHacer(voluntarioId);
    }
	public List<Proyecto> findAllProyectos() {
		// TODO Auto-generated method stub
		return proyectodao.findActivos();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Equipos> findAll() {
		// TODO Auto-generated method stub
		return equiposdao.findAll();
	}

	@Transactional
	@Override
	public void save(Equipos equipos) {
		 // Verificar si la Especie ya tiene un ID (es decir, si ya existe)
	    if (equipos.getId_equipos() != null) {
	        Equipos equiposExistente = equiposdao.findOne(equipos.getId_equipos());
	        
	        // Si la Especie existe, puedes realizar alguna lógica adicional
	        if (equiposExistente != null) {
	            // Aquí puedes actualizar algún campo si es necesario
	            // Ejemplo: especie.setNombre_especie(especieExistente.getNombre_especie());
	        }
	    }



	    // Guardar la Especie
	    equiposdao.save(equipos);
		
	}

	@Transactional(readOnly = true)
	@Override
	public Equipos findOne(Long id) {
		// TODO Auto-generated method stub
		return  equiposdao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		
		// Buscar la Especie por ID
	    Equipos equipos = equiposdao.findOne(id);
	    
	    if (equipos == null) {
	        throw new IllegalArgumentException("Equipos no encontrados");
	    }
	    
	  
	
	    equiposdao.delete(id);
	}
		



	
	@Override
	public List<Usuarios> listarVoluntariosPorProyecto(Long id) {
		// TODO Auto-generated method stub
		return equiposdao.listarVoluntariosPorProyecto(id);
	}

	@Override
	public List<Voluntarios> ObtenerVoluntario(String cedula) {
		// TODO Auto-generated method stub
		return equiposdao.ObtenerVoluntario(cedula);
	}

	@Override
	public List<Tipo_Actividades> listarActividades(Long id_proyecto) {
		// TODO Auto-generated method stub
		return equiposdao.listarActividades(id_proyecto);
	}

}
