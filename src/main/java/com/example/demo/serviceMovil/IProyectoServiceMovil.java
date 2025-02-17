package com.example.demo.serviceMovil;

import com.example.demo.entity.Proyecto;
import java.util.List;

public interface IProyectoServiceMovil {
    
    // Método para obtener proyectos con paginación
    List<Proyecto> findAllPaged(int page, int size);
    

}
