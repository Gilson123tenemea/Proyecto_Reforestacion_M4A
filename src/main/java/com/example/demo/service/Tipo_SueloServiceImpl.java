package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dao.ITipo_SueloDao;
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
