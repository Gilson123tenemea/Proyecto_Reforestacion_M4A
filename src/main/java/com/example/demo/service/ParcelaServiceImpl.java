package com.example.demo.service;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IAreaDao;
import com.example.demo.dao.IParcelaDao;
import com.example.demo.dao.IParroquiaDao;
import com.example.demo.dao.IProyectoDao;
import com.example.demo.entity.Area;
import com.example.demo.entity.Parcelas;
import com.example.demo.entity.Parroquia;
import com.example.demo.entity.Proyecto;
import com.example.demo.entity.Suelo;
import com.example.demo.entity.Tipo_Suelo;

@Service
public class ParcelaServiceImpl implements IParcelaService{

	@Autowired
	private IParcelaDao parceladao;
	@Autowired
	private IAreaDao areadao;
	@Autowired
	private IProyectoDao proyectodao;
	@Autowired
	private IParroquiaDao parroquiadao;
	
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

	@Override
	public List<Parcelas> findByAdministradorId(Long adminId) {
		return parceladao.findByAdministradorId(adminId);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Parcelas> listarparcelas() {
		
		return parceladao.findAll();
	}
	
	@Override
	public String findAreaName(Long idAreas) {
		 Parcelas parcelas = parceladao.findOne(idAreas);
		    if (parcelas != null) {
		        Long idarea = parcelas.getId_area();
		        Area area = areadao.findOne(idarea); 
		        return area != null ?area.getNombre() : null;
		    }
		    return null;
	}
	
	@Override
	public Object findArea(Long idParcela) {
	    Parcelas area = parceladao.findOne(idParcela);
	    if (area != null) {
	        Long idArea = area.getId_area(); 
	        Area areas = areadao.findOne(idArea); 
	        if (areas != null) {
	            return new Object() {
	                public Long id_area = areas.getId_area();
	                public String nombre = areas.getNombre();
	                public String tipo_terreno = areas.getTipo_terreno();
	                public String tipo_vegetacion = areas.getTipo_vegetacion();
	                public String observaciones = areas.getObservaciones();
	            };
	        } else {
	            System.out.println("No se encontró el área con ID: " + idArea);
	        }
	    } else {
	        System.out.println("No se encontró el proyecto con ID: " + idParcela);
	    }
	    return null;
	}
	
	@Override
	public String findParroquiaName(Long idAreas) {
	    
	    Parcelas parcelas = parceladao.findOne(idAreas);
	    
	    if (parcelas != null) {
	     
	        Long idArea = parcelas.getId_area();
	        
	        
	        Area area = areadao.findOne(idArea);
	        
	        if (area != null) {
	            
	            Long idProyecto = area.getId_proyecto();
	            
	            
	            Proyecto proyecto = proyectodao.findOne(idProyecto);
	            
	            if (proyecto != null) {
	               
	                Long idParroquia = proyecto.getId_parroquia();
	                
	               
	                Parroquia parroquia = parroquiadao.findOne(idParroquia);
	                
	                
	                return parroquia != null ? parroquia.getNombreParroquia() : null;
	            }
	        }
	    }
	    
	    
	    return null;
	}
	
	@Override
	public String findProyectoImg(Long idAreas) {
	    
	    Parcelas parcelas = parceladao.findOne(idAreas);
	    
	    if (parcelas != null) {
	     
	        Long idArea = parcelas.getId_area();
	        
	        
	        Area area = areadao.findOne(idArea);
	        
	        if (area != null) {
	            
	            Long idProyecto = area.getId_proyecto();
	            
	            
	            Proyecto proyecto = proyectodao.findOne(idProyecto);
	            
	            if (proyecto != null && proyecto.getImagen() != null) {
	                // Convertir la imagen a Base64
	                String imagenBase64 = Base64.getEncoder().encodeToString(proyecto.getImagen());
	                return imagenBase64;
	            }
	        }
	    }
	    
	    
	    return null;
	}
	
	
	

}
