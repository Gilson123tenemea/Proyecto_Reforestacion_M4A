package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.dao.IProyecto_ParticipacionDao;
import com.example.demo.entity.Proyecto_Participacion;
@Service
public class Proyecto_ParticipacionServiceImpl implements IProyecto_ParticipacionService {

	@Autowired
	private IProyecto_ParticipacionDao poyecto_participacionDao;
	
	@Transactional(readOnly=true)
	@Override
	public List<Proyecto_Participacion> findAll() {
		
		return  poyecto_participacionDao.findAll();
	}

	@Transactional
	@Override
	public void save(Proyecto_Participacion proyecto_participacion) {
		 poyecto_participacionDao.save(proyecto_participacion);
	}

	@Transactional(readOnly=true)
	@Override
	public Proyecto_Participacion findOne(Long id) {
		
		return  poyecto_participacionDao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		 poyecto_participacionDao.delete(id);
		
	}

}
