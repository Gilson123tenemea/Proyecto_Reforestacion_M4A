package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IAreaDao;
import com.example.demo.dao.IParcelaDao;
import com.example.demo.entity.Area;
import com.example.demo.entity.Parcelas;
import com.example.demo.entity.Suelo;
import com.example.demo.entity.Tipo_Suelo;

@Service
public class ParcelaServiceImpl implements IParcelaService{

	@Autowired
	private IParcelaDao parceladao;
	@Autowired
	private IAreaDao areadao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Parcelas> findAll() {
		// TODO Auto-generated method stub
		return parceladao.findAll();
	}
	
	@Transactional

	@Override
	public void save(Parcelas parcela) {
		// TODO Auto-generated method stub
		parceladao.save(parcela);
		
	}

	@Transactional(readOnly = true)

	@Override
	public Parcelas findOne(Long id) {
		// TODO Auto-generated method stub
		return parceladao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		parceladao.delete(id);
		
	}

	@Override
	public List<Parcelas> findByAreaId(Long idArea) {
		return parceladao.findByAreaId(idArea);
	}

	@Override
	public List<Parcelas> findByAdministradorId(Long adminId) {
		return parceladao.findByAdministradorId(adminId);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Parcelas> listarparcelas() {
		
		return parceladao.findAll();
	}
	
	@Override
	public String findAreaName(Long idAreas) {
		 Parcelas parcelas = parceladao.findOne(idAreas);
		    if (parcelas != null) {
		        Long idarea = parcelas.getId_area();
		        Area area = areadao.findOne(idarea); 
		        return area != null ?area.getNombre() : null;
		    }
		    return null;
	}
	
	
	
	
	

}
