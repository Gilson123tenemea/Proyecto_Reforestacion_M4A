package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Plantas;

public interface IPlantasDao {

	public List<Plantas> findAll();
	public void save(Plantas plantas);
	public Plantas findOne(Long id);
	public void delete(Long id);
}
