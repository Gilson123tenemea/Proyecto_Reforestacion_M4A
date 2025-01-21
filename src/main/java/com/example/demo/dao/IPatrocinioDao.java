package com.example.demo.dao;

import java.util.List;
import com.example.demo.entity.Patrocinador;
import com.example.demo.entity.Patrocinio;

public interface IPatrocinioDao {
	public List<Patrocinio> findAll();
	public void save(Patrocinio patrocinio);
	public Patrocinio findOne (Long id);
	public void delete(Long id);
}
