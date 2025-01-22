package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Parcelas;

public interface IParcelaService {
	public List<Parcelas> findAll();
	public void save(Parcelas parcela);
	public Parcelas findOne(Long id);
	public void delete(Long id);


}