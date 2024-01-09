package com.universo.company.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universo.company.entity.Empresa;
import com.universo.company.entity.Mercado;
import com.universo.company.repository.IEmpresaRepo;
import com.universo.company.repository.IMercadoRepo;
import com.universo.company.response.EmpresaResponseRest;
import com.universo.company.service.IEmpresaService;
import com.universo.company.util.Util;

@Service 
public class EmpresaServiceImpl implements IEmpresaService {

	private static final String TIPO_RESPUESTA_OK = "Respuesta ok";
	private static final String TIPO_RESPUESTA_NOK = "Respuesta nok";
	private static final String CODIGO_RESPUESTA_OK = "00";
	private static final String CODIGO_RESPUESTA_NOK = "-1";
	private static final String DESC_RESPUESTA_OK = "Respuesta exitosa";
	private static final String DESC_RESPUESTA_NOK = "Error al consultar";

	private static final String DESC_EMPRESA_CREADA_OK = "Empresa almacenada";
	private static final String DESC_EMPRESA_CREADA_NOK = "Empresa no almacenada";
	private static final String DESC_EMPRESA_CREADA_EXCEP = "Error al almacenar registro";
	private static final String DESC_EMPRESA_MERCADO_NOK = "Mercado no encontrado asociado a la empresa";
	
	private static final String DESC_EMPRESA_OK = "Empresa encontrada";
	private static final String DESC_EMPRESA_NOK = "Empresa no encontrada";
	private static final String DESC_EMPRESA_EXCEP = "Error al recuperar empresa";
	
	private static final String DESC_BUSQUEDA_EMPRESAS_OK = "Empresas encontradas";
	private static final String DESC_BUSQUEDA_EMPRESAS_NOK = "Empresas no encontradas";
	private static final String DESC_BUSQUEDA_EMPRESAS_EXCEP = "Error al recuperar empresas por literal";
	
	private static final String DESC_EMPRESA_ELIMINADA_OK = "Empresa eliminada";
	private static final String DESC_EMPRESA_ELIMINADA_EXCEP = "Error al eliminar empresa";
	
	private static final String DESC_OBTENCION_EMPRESAS_OK = "Empresas encontradas";
	private static final String DESC_OBTENCION_EMPRESAS_NOK = "Empresas no encontradas";
	private static final String DESC_OBTENCION_EMPRESAS_EXCEP = "Error al recuperar todos los productos";
	
	private static final String DESC_EMPRESA_MODIFICADA_OK = "Empresa actualizada";
	private static final String DESC_EMPRESA_MODIFICADA_NOK = "Empresa no actualizada";
	private static final String DESC_EMPRESA_MODIFICADA_INEX = "Mercado no encontrado asociado a la empresa";
	private static final String DESC_EMPRESA_MODIFICADA_EXCEP = "Error al actualizar registro";
	
	
	// Inyeccion de Repos
	@Autowired
	private IMercadoRepo mercadoRepo;
	@Autowired
	private IEmpresaRepo empresaRepo;
	
	
	/**
	 * Almacenar Empresa
	 * @param 	Empresa			Empresa a almacenar
	 * @param 	Long			Id del mercado de la empresa
	 * @return	ResponseEntity	Respuesta con la empresa
	 */
	@Override
	@Transactional
	public ResponseEntity<EmpresaResponseRest> save(Empresa empresa, Long mercadoId) {
		// TODO Auto-generated method stub

		EmpresaResponseRest response = new EmpresaResponseRest();
		
		List<Empresa> list = new ArrayList<>();
		
		try {
			
			// Buscar mercado que debe establecerse en el objeto Empresa
			Optional<Mercado> mercado = mercadoRepo.findById(mercadoId);
			
			if (mercado.isPresent()) {
				empresa.setMercado(mercado.get());
			} else {
				response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_EMPRESA_MERCADO_NOK);
				return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			// Almacenar el producto en BBDD
			Empresa empresaSaved = empresaRepo.save(empresa);
			
			if (empresaSaved != null) {
				list.add(empresaSaved);
				// Almacenamos la lista de empresas en el objeto EmpresaResponse para la aplicacion cliente
				response.getEmpresaResponse().setEmpresas(list); 
				response.setMetadata(TIPO_RESPUESTA_OK, CODIGO_RESPUESTA_OK, DESC_EMPRESA_CREADA_OK);
			} else {
				response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_EMPRESA_CREADA_NOK);
				return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.BAD_REQUEST); // error 404
			}
			
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_EMPRESA_CREADA_EXCEP);
			return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); // error 500
		}
		
		return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.OK); 
		
	}

	/**
	 * Buscar por Id de Empresa
	 * @param 	Long			Id de la empresa
	 * @return	ResponseEntity	Respuesta con la empresa
	 */
	@Override
	@Transactional (readOnly = true)
	public ResponseEntity<EmpresaResponseRest> searchById(Long empresaId) {
		// TODO Auto-generated method stub

		EmpresaResponseRest response = new EmpresaResponseRest();
		
		List<Empresa> list = new ArrayList<>();
		
		try {
			
			// Buscar Empresa por id
			Optional<Empresa> empresa = empresaRepo.findById(empresaId);
			
		    System.out.println("empresa: " + empresa.toString());
			
			if (empresa.isPresent()) {
				
				// Cambiamos la imagen de la empresa del formato BBDD al formato legible por la web
				byte[] imageBbdd = empresa.get().getImagen();
				byte[] imageDescompressed = Util.decompressZLib(imageBbdd);
				empresa.get().setImagen(imageDescompressed);
				
				System.out.println("empresa & image: " + empresa.toString());
				
				list.add(empresa.get());
				
				// Cargamos la info de la respuesta
				response.setMetadata(TIPO_RESPUESTA_OK, CODIGO_RESPUESTA_OK, DESC_EMPRESA_OK);
				response.getEmpresaResponse().setEmpresas(list);
				
				
			} else {
				response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_EMPRESA_NOK);
				return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_EMPRESA_EXCEP);
			return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); // error 500
		}
		
		return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.OK); 
		
	}

	/**
	 * Busqueda de empresas cuyo nombre contega un literal especificado
	 * @param 	cadena			Literal por el que se busca en el nombre de las empresas
	 * @return  ResponseEntity 	Respuesta con la lista de empresas encontradas
	 */
	@Override
	@Transactional (readOnly = true)
	public ResponseEntity<EmpresaResponseRest> searchByName(String cadena) {
		// TODO Auto-generated method stub

		EmpresaResponseRest response = new EmpresaResponseRest();
		
		List<Empresa> list = new ArrayList<>();
		List<Empresa> listAux = new ArrayList<>();
		
		try {
			
			// Buscar Empresa por cadena de Nombre
			listAux = empresaRepo.findByDescripcionContainingIgnoreCase(cadena);
			
			if (listAux.size() > 0) {
			
				// Recorremos la lista recuperando la info de cada empresa con una funcion lambda
				listAux.stream().forEach( (empresa) -> {
					// Cambiamos la imagen de la empresa del formato BBDD al formato legible por la web
					byte[] imageBbdd = empresa.getImagen();
					byte[] imageDescompressed = Util.decompressZLib(imageBbdd);
					empresa.setImagen(imageDescompressed);
					list.add(empresa);
				} );

				// Cargamos la info de la respuesta
				response.setMetadata(TIPO_RESPUESTA_OK, CODIGO_RESPUESTA_OK, DESC_BUSQUEDA_EMPRESAS_OK);
				response.getEmpresaResponse().setEmpresas(list);
				
				
			} else {
				response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_BUSQUEDA_EMPRESAS_NOK);
				return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_BUSQUEDA_EMPRESAS_EXCEP);
			return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); // error 500
		}
		
		return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.OK); 
		
	}

	/**
	 * Eliminar empresa por id
	 * @param	id				Id de la empresa
	 * @return	ResponseEntity	Respuesta con los metadatos de la ejecucion
	 */
	@Override
	@Transactional
	public ResponseEntity<EmpresaResponseRest> deleteById(Long id) {
		// TODO Auto-generated method stub
		
		EmpresaResponseRest response = new EmpresaResponseRest();
		
		try {
			
			// Eliminar Empresa por id
			empresaRepo.deleteById(id);
			
			response.setMetadata(TIPO_RESPUESTA_OK, CODIGO_RESPUESTA_OK, DESC_EMPRESA_ELIMINADA_OK);
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_EMPRESA_ELIMINADA_EXCEP);
			return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); // error 500
		}
		
		return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.OK); 
		
	}

	/**
	 * Obtencion de todas las empresas contenidas en la BBDD
	 * @return	ResponseEntity	Respuesta con todas las empresas de la BBDD
	 */
	@Override
	@Transactional (readOnly = true)
	public ResponseEntity<EmpresaResponseRest> searchAll() {
		// TODO Auto-generated method stub
		
		EmpresaResponseRest response = new EmpresaResponseRest();
		
		List<Empresa> list = new ArrayList<>();
		List<Empresa> listAux = new ArrayList<>();
		
		try {
			
			// Obtenemos todas las empresas de la BBDD
			listAux = (List<Empresa>) empresaRepo.findAll();
			
			if (listAux.size() > 0) {
			
				// Recorremos la lista recuperando la info de cada empresa con una funcion lambda
				listAux.stream().forEach( (empresa) -> {
					// Cambiamos la imagen de la empresa del formato BBDD al formato legible por la web
					byte[] imageBbdd = empresa.getImagen();
					byte[] imageDescompressed = Util.decompressZLib(imageBbdd);
					empresa.setImagen(imageDescompressed);
					list.add(empresa);
				} );

				// Cargamos la info de la respuesta
				response.setMetadata(TIPO_RESPUESTA_OK, CODIGO_RESPUESTA_OK, DESC_OBTENCION_EMPRESAS_OK);
				response.getEmpresaResponse().setEmpresas(list);
				
				
			} else {
				response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_OBTENCION_EMPRESAS_NOK);
				return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.NOT_FOUND);		// error 404
			}
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_OBTENCION_EMPRESAS_EXCEP);
			return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); // error 500
		}
		
		return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.OK); 
		
	}

	/**
	 * Actualizar empresa con los datos enviados
	 * @param	empresa			Datos con los que debe ser actualizada la empresa
	 * @param	mercadoId		Id del nuevo mercado de la empresa
	 * @param	empresaId		Id de la empresa que debe ser actualizada
	 * @param	ResponseEntity	Respuesta con la lista de empresas actualizadas
	 */
	@Override
	@Transactional
	public ResponseEntity<EmpresaResponseRest> update(Empresa empresa, Long mercadoId, Long empresaId) {
		// TODO Auto-generated method stub
		
		EmpresaResponseRest response = new EmpresaResponseRest();
		
		List<Empresa> list = new ArrayList<>();
		
		try {
			
			// Buscar Mercado que debe establecerse en el objeto Empresa
			Optional<Mercado> mercado = mercadoRepo.findById(mercadoId);
			
			if (mercado.isPresent()) {
				empresa.setMercado(mercado.get());
			} else {
				response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_EMPRESA_MODIFICADA_INEX);
				return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			// Buscar producto a actualizar
			Optional<Empresa> empresaSearch = empresaRepo.findById(empresaId);
			
			// Actualizamos los datos de la empresa recuperada con los datos recibidos en el metodo
			if (empresaSearch.isPresent()) {
				
				empresaSearch.get().setClave(empresa.getClave());
				
				empresaSearch.get().setDescripcion(empresa.getDescripcion());
				empresaSearch.get().setCapital(empresa.getCapital());
				empresaSearch.get().setMercado(mercado.get());
				empresaSearch.get().setImagen(empresa.getImagen());
				
				// Almacenamos empresa en base de datos
				Empresa empresaToUpdate = empresaRepo.save(empresaSearch.get());
				
				// Si la empresa se ha actualizado  correctamente
				if (empresaToUpdate != null){
					list.add(empresaToUpdate);

					// Almacenamos la lista de empresas en el objeto EmpresaResponse para la aplicacion cliente
					response.getEmpresaResponse().setEmpresas(list); 
					response.setMetadata(TIPO_RESPUESTA_OK, CODIGO_RESPUESTA_OK, DESC_EMPRESA_MODIFICADA_OK);
				} else {
					response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_EMPRESA_MODIFICADA_NOK);
					return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.BAD_REQUEST); // error 404
				}
				
			} else {
				response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_EMPRESA_MODIFICADA_NOK);
				return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.BAD_REQUEST); // error 404
			}
			
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_EMPRESA_MODIFICADA_EXCEP);
			return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); // error 500
		}
		
		return new ResponseEntity<EmpresaResponseRest>(response, HttpStatus.OK); 
		
	}

}
