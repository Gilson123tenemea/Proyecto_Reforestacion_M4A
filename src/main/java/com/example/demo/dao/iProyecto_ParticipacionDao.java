package com.example.demo.dao;

import java.util.List;
import com.example.demo.entity.Proyecto_Participacion;

public interface iProyecto_ParticipacionDao {
	public List<Proyecto_Participacion> findAll();
	public void save(Proyecto_Participacion proyecto_participacion);
	public Proyecto_Participacion findOne (Long id);
	public void delete(Long id);
}
