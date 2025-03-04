package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IProvinciaDao;
import com.example.demo.entity.Canton;
import com.example.demo.entity.Provincia;

@Service
public class ProvinciaServiceImpl implements IProvinciaService {

	@Autowired
	private IProvinciaDao provinciadao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Provincia> findAll() {
		// TODO Auto-generated method stub
		return provinciadao.findAll();
	}

	@Transactional
	@Override
	 public void save(Provincia provincia) {
        if (provincia.getId_provincia() != null) {
            Provincia provinciaActual = provinciadao.findOne(provincia.getId_provincia());
            if (provinciaActual != null) {
                for (Canton canton : provincia.getCanton()) {
                    if (canton.getId_provincia() == null) {
                        canton.setId_provincia(provincia.getId_provincia());
                    }
                }
            }
        }

        provinciadao.save(provincia);
    }

	
	@Transactional(readOnly = true)
	@Override
	public Provincia findOne(Long id) {
		// TODO Auto-generated method stub
		return provinciadao.findOne(id);
	}

	
	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		provinciadao.delete(id);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Provincia> listarProvincias() {
		return provinciadao.findAll();
	}
	
	@Override
	public long countCantonesByProvinciaId(Long idProvincia) {
	    return provinciadao.countCantonesByProvinciaId(idProvincia);
	}

}
