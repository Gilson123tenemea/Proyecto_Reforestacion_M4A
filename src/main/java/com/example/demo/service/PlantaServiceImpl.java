package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IPlantasDao;
import com.example.demo.entity.Area;
import com.example.demo.entity.Plantas;


@Service
public class PlantaServiceImpl implements IPlantasService {
	
	@Autowired
	private IPlantasDao plantadao;

	@Override
	public List<Plantas> findAll() {
		// TODO Auto-generated method stub
		return plantadao.findAll();
	}
    
	@Transactional
	@Override
	public void save(Plantas plantas) {
		// TODO Auto-generated method stub
		plantadao.save(plantas);
		
	}

	@Override
	public Plantas findOne(Long id) {
		// TODO Auto-generated method stub
		return plantadao.findOne(id);
	}
    
	@Transactional	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		plantadao.delete(id);
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Plantas> listarPlantas() {
		return plantadao.findAll();
	}
	
	@Override
	public long countParcelasByPlantaId(Long idPlanta) {
	    return plantadao.countParcelasByPlantaId(idPlanta);
	}
	

}
