package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Patrocinador;
import com.example.demo.entity.Voluntarios;


public interface IPatrocinadorDao {
	
	public List<Patrocinador> findAll();
	public void save(Patrocinador patrocinador);
	public Patrocinador findOne(Long id);
	public void delete(Long id);
	List<Patrocinador> PatrocinadorUsuario(Long id_patrocinador);


}
