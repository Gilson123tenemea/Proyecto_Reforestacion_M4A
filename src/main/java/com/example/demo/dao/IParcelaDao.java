package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Parcelas;
import com.example.demo.entity.Suelo;

public interface IParcelaDao {
	

	public List<Parcelas> findAll();
	public void save(Parcelas parcela);
	public Parcelas findOne(Long id);
	public void delete(Long id);
	List<Parcelas> findByAreaId(Long idArea);
	List<Parcelas> findByAdministradorId(Long adminId);
	public List<Parcelas> listarparcelas();
}
