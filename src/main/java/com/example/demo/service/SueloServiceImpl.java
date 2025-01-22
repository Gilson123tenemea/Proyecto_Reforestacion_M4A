package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.ISueloDao;
import com.example.demo.entity.Suelo;
@Service
public class SueloServiceImpl implements ISueloService {
	
	@Autowired
	private ISueloDao suelodao;

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

}
