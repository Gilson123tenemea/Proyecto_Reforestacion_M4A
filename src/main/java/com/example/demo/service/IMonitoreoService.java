package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Monitoreo;

public interface IMonitoreoService {
	

	public List<Monitoreo> findAll();
	public void save(Monitoreo monitoreo);
	public Monitoreo findOne(Long id);
	public void delete(Long id);

}
