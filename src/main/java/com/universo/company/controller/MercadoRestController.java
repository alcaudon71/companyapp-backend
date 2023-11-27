package com.universo.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.universo.company.entity.Mercado;
import com.universo.company.response.MercadoResponseRest;
import com.universo.company.service.IMercadoService;

// CrossOrigin ---> Permite el acceso desde una aplicacion FrontEnd externa a nuestro controlador BackEnd
// Habilitamos el acceso desde localhost:4200, que es el puerto por defecto utilizado por los FrontEnd de Angular
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/universo/company")
public class MercadoRestController {

	@Autowired
	private IMercadoService service;

	/**
	 * Controlador para invocar al servicio de obtencion de todas los mercados
	 * @return	ResponseEntity	Datos de todos los mercados existentes
	 */
	// endpoint: /universo/company/mercados
	@GetMapping("/mercados")
	public ResponseEntity<MercadoResponseRest> searchMercados() {
		
		ResponseEntity<MercadoResponseRest> response = service.search();
		
		return response;
		
	}

	/**
	 * Controlador para invocar al servicio de obtencion de mercados por Id
	 * @param  id   			ID del mercado
	 * @return ResponseEntity	Datos del mercado buscada
	 */
	// endpoint: /universo/company/mercados/{Id}
	// @PathVariable ---> recoge el endpoint de entrada en formato "Decoded"
	//                    http://localhost:8080//universo/company/mercados/abc
	// @RequestParam ---> recoge el endpoint de entrada en formato "Encoded"
	//  				  http://localhost:8080//universo/company/mercados?id=abc
	@GetMapping("/mercados/{id}")
	public ResponseEntity<MercadoResponseRest> searchCategoriesById(@PathVariable Long id) {
		
		//System.out.println(${app.inicio]);
		ResponseEntity<MercadoResponseRest> response = service.searchById(id);
		
		return response;
		
	}

	/**
	 * Controlador para invocar al servicio de creacion de mercado
	 * @param 	Mercado			Mercado a crear
	 * @return	ResponseEntity	Datos del mercado almacenado
	 */
	// endpoint: /universo/company/mercados
	// @RequestBody ---> recoge el envio en formato json y lo mapea con el objeto Mercado 
	@PostMapping("/mercados")
	public ResponseEntity<MercadoResponseRest> save (@RequestBody Mercado mercado) {
		 
		ResponseEntity<MercadoResponseRest> response = service.save(mercado);
		
		return response;
		
	}	

	/**
	 * Controlador para invocar al servicio de actualizacion de mercado
	 * @param mercado			Nuevos datos del mercado
	 * @param id				Identificador del mercado que debe ser actualizado
	 * @return ResponseEntity	Datos del mercado modificado
	 */
	// endpoint: /universo/company/mercados/{id}
	@PutMapping("/mercados/{id}")
	public ResponseEntity<MercadoResponseRest> update (@RequestBody Mercado mercado, @PathVariable Long id) {
		 
		ResponseEntity<MercadoResponseRest> response = service.update(mercado, id);
		
		return response;
		
	}	

	/**
	 * Controlador para invocar al servicio de eliminacion de mercado
	 * @param id				Identificador del mercado a eliminar
	 * @return ResponseEntity	Datos del mercado eliminado
	 */
	// endpoint: /universo/company/mercados/{id}
	@DeleteMapping("/mercados/{id}")
	public ResponseEntity<MercadoResponseRest> delete (@PathVariable Long id) {
		 
		ResponseEntity<MercadoResponseRest> response = service.deleteById(id);
		
		return response;
		
	}	
	
}
