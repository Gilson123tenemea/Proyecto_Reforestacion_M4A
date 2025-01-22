package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.ICantonDao;
import com.example.demo.entity.Canton;

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
		// TODO Auto-generated method stub
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

}
