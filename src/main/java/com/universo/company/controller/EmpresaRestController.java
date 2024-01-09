package com.universo.company.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.universo.company.entity.Empresa;
import com.universo.company.response.EmpresaResponseRest;
import com.universo.company.service.IEmpresaService;
import com.universo.company.util.EmpresaExcelExporter;
import com.universo.company.util.Util;

import jakarta.servlet.http.HttpServletResponse;

// CrossOrigin ---> Permite el acceso desde una aplicacion FrontEnd externa a nuestro controlador BackEnd
// Habilitamos el acceso desde localhost:4200, que es el puerto por defecto utilizado por los FrontEnd de Angular
@CrossOrigin(origins = {"http://localhost:4200"} )
@RestController
@RequestMapping("/universo/company")
public class EmpresaRestController {

	private static final String EXCEL_CONTENT_TYPE = "application/octet-stream";
	private static final String EXCEL_HEADER_KEY = "Content-Disposition";
	private static final String EXCEL_HEADER_VALUE = "attachment; filename=result_empresa.xlsx";
	
	@Autowired
	private IEmpresaService empresaService;

	/**
	 * Controlador para invocar al servicio de Almacenamiento de Empresa
	 * 		Se reciben los datos asociados a una empresa
	 * @param  	imagen			Recibimos foto en formato manipulable por Spring Boot
	 * @param	clave 			Ticker de la empresa
	 * @param	descripcion		Descripcion de la empresa
	 * @param	capital			Capitalizacion de la empresa
	 * @param	mercadoId		Identificador de Mercado de la Empresa
	 * @return	ResponseEntity 	Datos de las empresas buscadas
	 * @throws 	IOException
	 */
	// endpoint: /universo/company/empresas/
	// @PathVariable ---> recoge el endpoint de entrada en formato "Decoded"
	//                    http://localhost:8080/universo/company/empresas
	// @RequestParam ---> recoge el endpoint de entrada en formato "Encoded"
	//  				  http://localhost:8080/universo/company/empresas
	@PostMapping("/empresas")
	public ResponseEntity<EmpresaResponseRest> save(
			@RequestParam("imagen") MultipartFile imagen,
			@RequestParam("clave") String clave,
			@RequestParam("descripcion") String descripcion,
			@RequestParam("capital") int capital,
			@RequestParam("mercadoId") Long mercadoId ) throws IOException {
		
		Empresa empresa = new Empresa();
		
		// Comprimir foto a formato base64 para almacenar en BBDD
		byte[] pictureBbdd = Util.compressZLib(imagen.getBytes());
		
		empresa.setClave(clave);
		empresa.setDescripcion(descripcion);
		empresa.setCapital(capital);
		empresa.setImagen(pictureBbdd);
		
		// Invocamos al servicio de Almacenar Empresa
		ResponseEntity<EmpresaResponseRest> response = empresaService.save(empresa, mercadoId);
		
		return response;
		
	}

	/**
	 * Obtener Empresa por Id
	 * @param 	id				Identificador de la Empresa
	 * @return 	ResponseEntity	Respuesta con la Empresa recuperada
	 */
	// endpoint: /universo/company/empresas/{id}
	// @PathVariable ---> recoge el endpoint de entrada en formato "Decoded"
	//                    http://localhost:8080/universo/company/empresas/abc
	// @RequestParam ---> recoge el endpoint de entrada en formato "Encoded"
	//  				  http://localhost:8080/universo/company/empresas?id=abc
	@GetMapping("empresas/{id}")
	public ResponseEntity<EmpresaResponseRest> searchById(@PathVariable Long id) {
		
		// Invocamos al servicio para recuperar la Empresa correspondiente al Id
		ResponseEntity<EmpresaResponseRest> response = empresaService.searchById(id);
		
		System.out.println("EmpresaResponse: " + response.toString() );
		
		return response;
		
	}
	
	/**
	 * Busqueda de Empresas cuyo nombre contiene una cadena especificada
	 * @param 	cadena			Cadena por la que se van a filtrar los nombres de las empresas
	 * @return	ResponseEntity	Respuesta con todas las empresas cuyo nombre contiene la cadena especificada
	 */
	@GetMapping("empresa/filter/{cadena}")
	public ResponseEntity<EmpresaResponseRest> searchByName(@PathVariable String cadena) {
		
		// Invocamos al servicio para recuperar la Empresa correspondiente a la cadena
		ResponseEntity<EmpresaResponseRest> response = empresaService.searchByName(cadena);
		
		return response;
		
	}
	
	/**
	 * Eliminar empresa por id
	 * @param 	id				Identificador de la empresa a eliminar	
	 * @return	ResponseEntity	Respuesta con los metadatos de la ejecucion
	 */
	@DeleteMapping("empresas/{id}")
	public ResponseEntity<EmpresaResponseRest> deleteById(@PathVariable Long id) {
		
		// Invocamos al servicio para eliminar la Empresa correspondiente al Id
		ResponseEntity<EmpresaResponseRest> response = empresaService.deleteById(id);
		
		return response;
		
	}
	
	/**
	 * Obtencion de todas las empresas existentes en la aplicacion 
	 * @return	ResponseEntity	Respuesta con todas las empresas de la BBDD
	 */
	@GetMapping("empresas")
	public ResponseEntity<EmpresaResponseRest> searchByName() {
		
		// Invocamos al servicio para recuperar todas las empresas de la aplicacion 
		ResponseEntity<EmpresaResponseRest> response = empresaService.searchAll();
		
		return response;
		
	}
	
	/**
	 * Actualizar empresa con los nuevos datos proporcionados
	 * @param 	imagen			Nueva imagen asociada a la empresa
	 * @param 	clave			Nueva clave asociada a la empresa
	 * @param 	descripcion		Nueva descruocuib asociada a la empresa
	 * @param 	capital			Nueva capitalizacion asociada a la empresa
	 * @param 	mercadoId		Nuevo mercado que debe ser asociada a la empresa
	 * @param 	empresaId		Identificador de la empresa que debe ser actualizado			
	 * @return	ResponseEntity	Respuesta con la list de empresas actualizada
	 * @throws IOException
	 */
	@PutMapping("empresas/{id}")
	public ResponseEntity<EmpresaResponseRest> update(
			@RequestParam("imagen") MultipartFile imagen,		// Estos parametros vienen en el body
			@RequestParam("clave") String clave,
			@RequestParam("descripcion") String descripcion,
			@RequestParam("capital") int capital,
			@RequestParam("mercadoId") Long mercadoId,
			@PathVariable Long id ) 							// Este parametro viene en la url 
					throws IOException {
		
		Empresa empresa = new Empresa();
		
		// Comprimir foto a formato base64 para almacenar en BBDD
		byte[] pictureBbdd = Util.compressZLib(imagen.getBytes());
		
		empresa.setClave(clave);
		empresa.setDescripcion(descripcion);
		empresa.setDescripcion(descripcion);
		empresa.setCapital(capital);

        empresa.setImagen(pictureBbdd);
		
		// Invocamos al servicio de Actualizar Empresa
		ResponseEntity<EmpresaResponseRest> response = empresaService.update(empresa, mercadoId, id);
		
		return response;
		
	}

	/**
	 * Controlador para invocar al servicio de Almacenar empresas en fichero excel
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/empresas/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		
		// Indicamos el tipo de contenido
		response.setContentType(EXCEL_CONTENT_TYPE);
		
		String headerKey = EXCEL_HEADER_KEY;
		String headerValue = EXCEL_HEADER_VALUE;  // nombre del archivo excel xml (posterior a 2007)
		
		// Establecemos el header con la configuracion establecida
		response.setHeader(headerKey, headerValue);
		
		// Obtenemos la lista de empresas del sistema 
		ResponseEntity<EmpresaResponseRest> empresaResponse = empresaService.searchAll();
		
		List<Empresa> listaEmpresas = empresaResponse.getBody().getEmpresaResponse().getEmpresas();
		
		// Generamos fichero excel con la lista de empresas
		EmpresaExcelExporter excelExporter = new EmpresaExcelExporter(listaEmpresas);
		
		excelExporter.export(response);
		
	}
	
}
