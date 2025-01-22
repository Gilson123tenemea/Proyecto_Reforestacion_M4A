package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Plantas;

public interface IPlantasService {
	public List<Plantas> findAll();
	public void save(Plantas plantas);
	public Plantas findOne(Long id);
	public void delete(Long id);
}
