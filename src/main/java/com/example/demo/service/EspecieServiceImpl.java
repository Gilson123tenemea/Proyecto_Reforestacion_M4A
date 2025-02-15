package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IEspecieDao;
import com.example.demo.entity.Especie;
import com.example.demo.entity.Plantas;
@Service

public class EspecieServiceImpl implements IEspecieService {
	
	@Autowired
	private IEspecieDao especiedao;

	@Override
	public List<Especie> findAll() {
		// TODO Auto-generated method stub
		return especiedao.findAll();
	}

	@Transactional
	@Override
	public void save(Especie especie) {
	    // Verificar si la Especie ya tiene un ID (es decir, si ya existe)
	    if (especie.getId_especie() != null) {
	        Especie especieExistente = especiedao.findOne(especie.getId_especie());
	        
	        // Si la Especie existe, puedes realizar alguna lógica adicional
	        if (especieExistente != null) {
	            // Aquí puedes actualizar algún campo si es necesario
	            // Ejemplo: especie.setNombre_especie(especieExistente.getNombre_especie());
	        }
	    }

	    // Verificar si la Especie tiene alguna relación con otras entidades
	    if (especie.getPlantas() != null) {
	        for (Plantas plantas : especie.getPlantas()) {
	            // Si la relación no tiene un ID, asignamos el ID de la Especie actual
	            if (plantas.getId_especie() == null) {
	            	plantas.setId_especie(especie.getId_especie());
	            }
	        }
	    }

	    // Guardar la Especie
	    especiedao.save(especie);
	}

	@Override
	public Especie findOne(Long id) {
		// TODO Auto-generated method stub
		return  especiedao.findOne(id);
	}
 
	
	
	@Transactional
	@Override
	public void delete(Long id) {
	    // Buscar la Especie por ID
	    Especie especie = especiedao.findOne(id);
	    
	    if (especie == null) {
	        throw new IllegalArgumentException("Especie no encontrada");
	    }
	    
	    // Verificar si tiene plantas asociadas
	    if (especie.getPlantas() != null && !especie.getPlantas().isEmpty()) {
	        throw new IllegalStateException("No se puede eliminar la Especie porque tiene Plantas asociadas");
	    }
	    
	    // Eliminar la Especie
	    especiedao.delete(id);
	}
	
	@Override
    public Especie findByNombre(String nombre) {
        return especiedao.findByNombre(nombre);
    }


}
