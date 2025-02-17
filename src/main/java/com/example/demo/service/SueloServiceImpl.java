package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.ISueloDao;
import com.example.demo.dao.ITipo_SueloDao;
import com.example.demo.entity.Area;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.Suelo;
import com.example.demo.entity.Tipo_Suelo;
@Service
public class SueloServiceImpl implements ISueloService {
	
	@Autowired
	private ISueloDao suelodao;

	@Autowired
	private ITipo_SueloDao tsuelodao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Suelo> findAll() {
		// TODO Auto-generated method stub
		return suelodao.findAll();
	}

	@Transactional
	@Override
	public void save(Suelo suelo) {
		// TODO Auto-generated method stub
		suelodao.save(suelo);
		
	}

	@Transactional(readOnly = true)
	@Override
	public Suelo findOne(Long id) {
		// TODO Auto-generated method stub
		return suelodao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		suelodao.delete(id);
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Suelo> listarsuelos() {
		return suelodao.findAll();
	}
	
	@Override
	public String findSueloName(Long idSuelo) {
		 Suelo suelo = suelodao.findOne(idSuelo);
		    if (suelo != null) {
		        Long idtiposuelo = suelo.getId_tiposuelo();
		        Tipo_Suelo tsuelo = tsuelodao.findOne(idtiposuelo); 
		        return tsuelo != null ?tsuelo.getNombre_suelo() : null;
		    }
		    return null;
	}


}
