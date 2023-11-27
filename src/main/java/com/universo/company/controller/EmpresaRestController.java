package com.universo.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universo.company.service.IEmpresaService;

// CrossOrigin ---> Permite el acceso desde una aplicacion FrontEnd externa a nuestro controlador BackEnd
// Habilitamos el acceso desde localhost:4200, que es el puerto por defecto utilizado por los FrontEnd de Angular
@CrossOrigin(origins = {"http://localhost:4200"} )
@RestController
@RequestMapping("/universo/company")
public class EmpresaRestController {

	@Autowired
	private IEmpresaService service;

	
}
