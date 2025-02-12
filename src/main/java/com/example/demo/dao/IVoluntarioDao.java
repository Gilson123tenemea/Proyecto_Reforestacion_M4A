package com.example.demo.dao;

import java.util.List;
import com.example.demo.entity.Voluntarios;

public interface IVoluntarioDao {
	public List<Voluntarios> findAll();
	public void save(Voluntarios voluntarios);
	public Voluntarios findOne (Long id);
	public void delete(Long id);
	List<Voluntarios> findAdministradoresWithUsuarios(Long iVoluntario);
	public int BuscarCedulaVoluntario(String cedula);
}
