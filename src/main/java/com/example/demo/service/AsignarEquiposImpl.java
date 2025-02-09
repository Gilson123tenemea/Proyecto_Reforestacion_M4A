package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IAsignarEquiposDao;
import com.example.demo.dao.IProyectoDao;
import com.example.demo.entity.Asignar_equipos;
import com.example.demo.entity.Equipos;
import com.example.demo.entity.Proyecto;

@Service
public class AsignarEquiposImpl implements IAsignacionEquiposService {

	@Autowired
	private IAsignarEquiposDao asignarequidao;
	
	@Autowired
	private IProyectoDao proyectodao;
	
	
	
	@Transactional(readOnly = true)
	@Override
	public List<Asignar_equipos> findAll() {
		// TODO Auto-generated method stub
		return asignarequidao.findAll();
	
	}

	@Transactional
	@Override
	public void save(Asignar_equipos asignar_equipos) {
		// Verificar si la Especie ya tiene un ID (es decir, si ya existe)
	    if (asignar_equipos.getId_asignar_equipos() != null) {
	    	Asignar_equipos equiposExistente = asignarequidao.findOne(asignar_equipos.getId_asignar_equipos());
	        
	        // Si la Especie existe, puedes realizar alguna lógica adicional
	        if (equiposExistente != null) {
	            // Aquí puedes actualizar algún campo si es necesario
	            // Ejemplo: especie.setNombre_especie(especieExistente.getNombre_especie());
	        }
	    }



	    // Guardar la Especie
	    asignarequidao.save(asignar_equipos);
	}

	@Transactional(readOnly = true)
	@Override
	public Asignar_equipos findOne(Long id) {
		// TODO Auto-generated method stub
		return  asignarequidao.findOne(id);
	}

	@Override
	public void delete(Long id) {
		// Buscar la Especie por ID
		Asignar_equipos asigequipos = asignarequidao.findOne(id);
	    
	    if (asigequipos == null) {
	        throw new IllegalArgumentException("Asignacion de Equipos no encontrados");
	    }
	    
	  
	
	    asignarequidao.delete(id);

	}

	@Override
	public List<Asignar_equipos> findByAsignarEquipos(Long id_asignar_equipos) {
		// TODO Auto-generated method stub
		return null;
	}

}
