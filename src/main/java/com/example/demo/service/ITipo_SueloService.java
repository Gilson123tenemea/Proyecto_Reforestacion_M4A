package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Tipo_Suelo;

@Service
public interface ITipo_SueloService {

	
    public List<Tipo_Suelo> findAll();
	
	public void save (Tipo_Suelo tpsuelo);
	
	public Tipo_Suelo findOne (Long id_tiposuelo);

	public void delete(Long id_tiposuelo);
}
