package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IPlantasDao;
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

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		plantadao.delete(id);
		
	}

}
