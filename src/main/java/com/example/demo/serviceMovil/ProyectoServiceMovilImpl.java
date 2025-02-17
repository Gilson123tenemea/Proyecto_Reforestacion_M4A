package com.example.demo.serviceMovil;

import com.example.demo.entity.Proyecto;
import com.example.demo.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProyectoServiceMovilImpl implements IProyectoServiceMovil {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Override
    public List<Proyecto> findAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return proyectoRepository.findAll(pageable).getContent();
    }

   
}
