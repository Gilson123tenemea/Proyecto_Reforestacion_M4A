package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.IProyectoDao;
import com.example.demo.entity.Plantas;
import com.example.demo.entity.Proyecto;

@Service
public class ProyectoServicelmpl implements IProyectoServices{

	@Autowired
	private IProyectoDao proyectodao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Proyecto> findAll() {
		return proyectodao.findAll();
	}

	@Transactional
	@Override
	public void save(Proyecto proyecto) {	
		proyectodao.save(proyecto);
	}

    @Transactional
    public void saveWithImage(String nombre, MultipartFile file) throws IOException {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre(nombre);
        proyecto.setImagen(file.getBytes());
        proyectodao.save(proyecto);
    }
	
	@Transactional(readOnly = true)
	@Override
	public Proyecto findOne(Long id) {
		return proyectodao.findOne(id);
	}

	@Transactional
	@Override
	public void delete(Long id) {
		proyectodao.delete(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Proyecto> listarproyectos() {
		return proyectodao.findAll();
	}
	
	
}
