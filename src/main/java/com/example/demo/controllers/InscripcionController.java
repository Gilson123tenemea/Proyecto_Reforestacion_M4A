package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.IInscripcionDao;
import com.example.demo.entity.Inscripcion;
import com.example.demo.service.IInscripcionServices;

@Controller
public class InscripcionController {
	
	@Autowired
	private IInscripcionServices inscripcionService;
	
	

}
