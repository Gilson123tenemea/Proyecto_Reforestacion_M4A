package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IMonitoreoDao;
import com.example.demo.entity.Monitoreo;

@Service

public class MonitoreoServiceImpl implements IMonitoreoService{

	@Autowired
	
	private IMonitoreoDao monitoreodao;
	
	
	@Transactional(readOnly = true)
	@Override
	public List<Monitoreo> findAll() {
		// TODO Auto-generated method stub
		return monitoreodao.findAll();
	}

	
	@Transactional
	@Override
	public void save(Monitoreo monitoreo) {
		// TODO Auto-generated method stub
		monitoreodao.save(monitoreo);
		
	}

	@Transactional(readOnly = true)
	@Override
	public Monitoreo findOne(Long id) {
		// TODO Auto-generated method stub
		return monitoreodao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		monitoreodao.delete(id);
		
	}

}
