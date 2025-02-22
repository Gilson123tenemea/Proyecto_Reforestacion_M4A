package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.ICantonDao;
import com.example.demo.entity.Canton;
import com.example.demo.entity.Parroquia;

@Service
public class CantonServiceImpl implements ICantonService {
	
	@Autowired
	private ICantonDao cantondao;

	
	@Transactional(readOnly = true)
	@Override
	public List<Canton> findAll() {
		// TODO Auto-generated method stub
		return cantondao.findAll();
	}

	@Transactional
	@Override
	public void save(Canton canton) {
        if (canton.getId_canton() != null) {
        	Canton cantonActual = cantondao.findOne(canton.getId_canton());
            if (cantonActual != null) {
                for (Parroquia parroquia : canton.getParroquia()) {
                    if (parroquia.getId_canton() == null) {
                    	parroquia.setId_canton(canton.getId_canton());
                    }
                }
            }
        }

        cantondao.save(canton);
    }

	@Transactional(readOnly = true)
	@Override
	public Canton findOne(Long id) {
		// TODO Auto-generated method stub
		return cantondao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		cantondao.delete(id);
		
	}

	@Override
	public List<Canton> listarCanton() {
		// TODO Auto-generated method stub
		return cantondao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Canton> findByProvincia(Long idProvincia) {
		 return cantondao.findByProvincia(idProvincia); 
	}
	
	@Override
	public long countParroquiasByCantonId(Long idCanton) {
	    return cantondao.countParroquiasByCantonId(idCanton);
	}
	
	

}
