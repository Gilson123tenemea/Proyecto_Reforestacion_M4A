package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.IEspecieDao;
import com.example.demo.entity.Especie;
@Service

public class EspecieServiceImpl implements IEspecieService {
	
	@Autowired
	private IEspecieDao especiedao;

	@Override
	public List<Especie> findAll() {
		// TODO Auto-generated method stub
		return especiedao.findAll();
	}

	@Override
	public void save(Especie especie) {
		// TODO Auto-generated method stub
		
		especiedao.save(especie);
	}

	@Override
	public Especie findOne(Long id) {
		// TODO Auto-generated method stub
		return  especiedao.findOne(id);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		especiedao.delete(id);
		
	}

}
