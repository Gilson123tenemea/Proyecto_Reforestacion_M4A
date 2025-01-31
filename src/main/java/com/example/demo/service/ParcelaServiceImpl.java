package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IParcelaDao;
import com.example.demo.entity.Parcelas;

@Service
public class ParcelaServiceImpl implements IParcelaService{

	@Autowired
	private IParcelaDao parceladao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Parcelas> findAll() {
		// TODO Auto-generated method stub
		return parceladao.findAll();
	}
	
	@Transactional

	@Override
	public void save(Parcelas parcela) {
		// TODO Auto-generated method stub
		parceladao.save(parcela);
		
	}

	@Transactional(readOnly = true)

	@Override
	public Parcelas findOne(Long id) {
		// TODO Auto-generated method stub
		return parceladao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		parceladao.delete(id);
		
	}

	@Override
	public List<Parcelas> findByAreaId(Long idArea) {
		return parceladao.findByAreaId(idArea);
	}

}
