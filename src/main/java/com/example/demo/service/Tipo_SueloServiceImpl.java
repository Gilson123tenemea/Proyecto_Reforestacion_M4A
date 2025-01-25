package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dao.ITipo_SueloDao;
import com.example.demo.entity.Suelo;
import com.example.demo.entity.Tipo_Suelo;


@Service

public class Tipo_SueloServiceImpl implements ITipo_SueloService {

	@Autowired
	private ITipo_SueloDao tpsueloDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Tipo_Suelo> findAll() {
		
		return tpsueloDao.findAll();
	}

	@Transactional
	@Override
	public void save(Tipo_Suelo tipo_suelos) {
	    // Verificar si el Tipo de Suelo ya tiene un ID (es decir, si ya existe)
	    if (tipo_suelos.getId_tiposuelo() != null) {
	        Tipo_Suelo tipoSueloExistente = tpsueloDao.findOne(tipo_suelos.getId_tiposuelo());
	        
	        // Si el Tipo de Suelo existe, puedes realizar alguna lógica adicional
	        if (tipoSueloExistente != null) {
	            // Aquí puedes actualizar algún campo si es necesario
	            // Ejemplo: tipo_suelos.setNombre_suelo(tipoSueloExistente.getNombre_suelo());
	        }
	    }

	    // Verificar si el Tipo de Suelo tiene alguna relación con otras entidades, como suelos
	    if (tipo_suelos.getSuelo() != null) {
	        for (Suelo suelo : tipo_suelos.getSuelo()) {
	            // Si el Suelo no tiene un ID de Tipo de Suelo, asignamos el ID del Tipo de Suelo actual
	            if (suelo.getId_tiposuelo() == null) {
	                suelo.setId_tiposuelo(tipo_suelos.getId_tiposuelo());
	            }
	        }
	    }

	    // Guardar el Tipo de Suelo
	    tpsueloDao.save(tipo_suelos);
	}

	

	@Transactional(readOnly=true)
	@Override
	public Tipo_Suelo findOne(Long id) {
		
		return tpsueloDao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
	    // Buscar el Tipo_Suelo por ID
	    Tipo_Suelo tipoSuelo = tpsueloDao.findOne(id);
	    
	    if (tipoSuelo == null) {
	        throw new IllegalArgumentException("Tipo de Suelo no encontrado");
	    }
	    
	    // Verificar si tiene suelos asociados
	    if (tipoSuelo.getSuelo() != null && !tipoSuelo.getSuelo().isEmpty()) {
	        throw new IllegalStateException("No se puede eliminar el Tipo de Suelo porque tiene Suelos asociados");
	    }
	    
	    // Eliminar el Tipo_Suelo
	    tpsueloDao.delete(id);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Tipo_Suelo> listartiposuelos() {
		return tpsueloDao.findAll();
	}

}
