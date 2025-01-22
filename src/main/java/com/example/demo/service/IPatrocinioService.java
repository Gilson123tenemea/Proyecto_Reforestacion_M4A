package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Patrocinio;

@Service
public interface IPatrocinioService {

	public List<Patrocinio> findAll();
	public void save(Patrocinio patrocinio);
	public Patrocinio findOne (Long id);
	public void delete(Long id);
	
}
