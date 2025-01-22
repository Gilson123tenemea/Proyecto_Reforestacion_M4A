package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IParroquiaDao;
import com.example.demo.entity.Parroquia;

@Service
public class ParroquiaServiceImpl implements IParroquiaService{

	@Autowired
	private IParroquiaDao parroquiadao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Parroquia> findAll() {
		// TODO Auto-generated method stub
		return parroquiadao.findAll();
	}

	@Transactional
	@Override
	public void save(Parroquia parroquia) {
		// TODO Auto-generated method stub
		parroquiadao.save(parroquia);
		
	}

	@Transactional(readOnly = true)
	@Override
	public Parroquia findOne(Long id) {
		// TODO Auto-generated method stub
		return parroquiadao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		parroquiadao.delete(id);
		
	}

}
