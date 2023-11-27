package com.universo.company.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.universo.company.entity.Mercado;
import com.universo.company.repository.IMercadoRepo;
import com.universo.company.response.MercadoResponseRest;
import com.universo.company.service.IMercadoService;

@Service 
public class MercadoServiceImpl implements IMercadoService {

	private static final String TIPO_RESPUESTA_OK = "Respuesta ok";
	private static final String TIPO_RESPUESTA_NOK = "Respuesta nok";
	private static final String CODIGO_RESPUESTA_OK = "00";
	private static final String CODIGO_RESPUESTA_NOK = "-1";
	private static final String DESC_RESPUESTA_OK = "Respuesta exitosa";
	private static final String DESC_RESPUESTA_NOK = "Error al consultar";
	
	private static final String DESC_MERCADO_OK = "Mercado encontrado";
	private static final String DESC_MERCADO_NOK = "Mercado no encontrado";
	private static final String DESC_MERCADO_EXCEP = "Error al consultar por Id";
	private static final String DESC_MERCADO_CREADO_OK = "Mercado almacenado";
	private static final String DESC_MERCADO_CREADO_NOK = "Mercado no almacenado";
	private static final String DESC_MERCADO_CREADO_EXCEP = "Error al almacenar registro";
	private static final String DESC_MERCADO_MODIFICADO_OK = "Mercado actualizado";
	private static final String DESC_MERCADO_MODIFICADO_NOK = "Mercado no actualizado";
	private static final String DESC_MERCADO_MODIFICADO_INEX = "Mercado no encontrado";
	private static final String DESC_MERCADO_MODIFICADO_EXCEP = "Error al actualizar registro";
	private static final String DESC_MERCADO_ELIMINADO_OK = "Registro eliminado";
	private static final String DESC_MERCADO_ELIMINADO_EXCEP = "Error al eliminar";
	
	
	// Inyectar el objeto al contenedor de Spring
	// El objeto queda instanciado de forma automatica
	@Autowired
	private IMercadoRepo mercadoRepo;
	
	// Declaracion de metodo transaccional (acceso a bbdd)
	// Gestiona automaticamente los rollback en las excepciones
	@Override
	@Transactional(readOnly=true)
	public ResponseEntity<MercadoResponseRest> search() {
		// TODO Auto-generated method stub

		MercadoResponseRest response = new MercadoResponseRest();
		
		try {
			
			List<Mercado> mercado = (List<Mercado>) mercadoRepo.findAll(); 
			
			response.getMercadoResponse().setMercado(mercado);
			
			// Rellenar el metadata de la respuesta
			response.setMetadata(TIPO_RESPUESTA_OK, CODIGO_RESPUESTA_OK, DESC_RESPUESTA_OK);
			
		} catch (Exception e) {
			
			response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_RESPUESTA_NOK);
			e.getStackTrace();
			
			ResponseEntity<MercadoResponseRest> respError = new ResponseEntity<MercadoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			return respError;
			
		}
		
		ResponseEntity<MercadoResponseRest> respuesta = new ResponseEntity<MercadoResponseRest>(response, HttpStatus.OK);
		
		return respuesta;
		
	}

	/**
	 * Busqueda de un Mercado por Id
	 */
	@Transactional(readOnly=true)
	@Override
	public ResponseEntity<MercadoResponseRest> searchById(Long id) {
		// TODO Auto-generated method stub

		MercadoResponseRest response = new MercadoResponseRest();
		List<Mercado> list = new ArrayList<>();
		
		try {
			
			Optional<Mercado> mercado = mercadoRepo.findById(id);
			
			if (mercado.isPresent()) {
				list.add(mercado.get());
				response.getMercadoResponse().setMercado(list);
				// Rellenar el metadata de la respuesta
				response.setMetadata(TIPO_RESPUESTA_OK, CODIGO_RESPUESTA_OK, DESC_MERCADO_OK);
			} else {
				response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_MERCADO_NOK);
				ResponseEntity<MercadoResponseRest> respError = new ResponseEntity<MercadoResponseRest>(response, HttpStatus.NOT_FOUND);
				return respError;
			}
			
		} catch (Exception e) {
			
			response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_MERCADO_EXCEP);
			e.getStackTrace();
			
			ResponseEntity<MercadoResponseRest> respError = new ResponseEntity<MercadoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			return respError;
			
		}
		
		ResponseEntity<MercadoResponseRest> respuesta = new ResponseEntity<MercadoResponseRest>(response, HttpStatus.OK);
		
		return respuesta;
		
	}

	/**
	 * Guardar mercado
	 */
	@Transactional
	@Override
	public ResponseEntity<MercadoResponseRest> save(Mercado mercado) {
		// TODO Auto-generated method stub
		
		MercadoResponseRest response = new MercadoResponseRest();
		List<Mercado> list = new ArrayList<>();
		
		try {
			
			// Almacenamos registro en BBDD
			// Como respuesta, recibimos los datos del objeto almacenado
			Mercado mercadoSaved = mercadoRepo.save(mercado);
			
			// Si se ha almacenado correctamente, cargamos objeto en la lista que se va a devolver en la respuesta
			if (mercadoSaved != null) {
				list.add(mercadoSaved);
				response.getMercadoResponse().setMercado(list);
				
				response.setMetadata(TIPO_RESPUESTA_OK, CODIGO_RESPUESTA_OK, DESC_MERCADO_CREADO_OK);
			} else {
				response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_MERCADO_CREADO_NOK);
				ResponseEntity<MercadoResponseRest> respError = 
											new ResponseEntity<MercadoResponseRest>(response, HttpStatus.BAD_REQUEST);
				return respError;
			}
				
		} catch (Exception e) {
			
			response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_MERCADO_CREADO_EXCEP);
			e.getStackTrace();
			
			ResponseEntity<MercadoResponseRest> respError = new ResponseEntity<MercadoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			return respError;
			
		}
		
		ResponseEntity<MercadoResponseRest> respuesta = new ResponseEntity<MercadoResponseRest>(response, HttpStatus.OK);
		
		return respuesta;
		
	}

	/**
	 * Actualizar mercado
	 */
	@Override
	@Transactional
	public ResponseEntity<MercadoResponseRest> update(Mercado mercado, Long id) {
		// TODO Auto-generated method stub
		
		MercadoResponseRest response = new MercadoResponseRest();
		List<Mercado> list = new ArrayList<>();
		
		try {
			
			// Verificamos si existe el id que tenemos que modificar
			Optional<Mercado> mercadoSearch = mercadoRepo.findById(id);
			
			if (mercadoSearch.isPresent()) {
				// Actualizamos el registro 
				mercadoSearch.get().setClave(mercado.getClave());
				mercadoSearch.get().setDescripcion(mercado.getDescripcion());
				mercadoSearch.get().setPais(mercado.getPais());
				mercadoSearch.get().setSector(mercado.getSector());
				
				Mercado mercadoToUpdate = mercadoRepo.save(mercadoSearch.get());
				
				// Actualizacion correcta en Repo
				if (mercadoToUpdate != null) {
					list.add(mercadoToUpdate);
					response.getMercadoResponse().setMercado(list);
					response.setMetadata(TIPO_RESPUESTA_OK, CODIGO_RESPUESTA_OK, DESC_MERCADO_MODIFICADO_OK);
				} else {
					// Error de actualizacion en Dao
					response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_MERCADO_MODIFICADO_NOK);
					ResponseEntity<MercadoResponseRest> respError = 
												new ResponseEntity<MercadoResponseRest>(response, HttpStatus.BAD_REQUEST);
					return respError;
				}
				
				
				
			} else {
				response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_MERCADO_MODIFICADO_INEX);
				ResponseEntity<MercadoResponseRest> respError = 
											new ResponseEntity<MercadoResponseRest>(response, HttpStatus.NOT_FOUND);
				return respError;
			}

				
		} catch (Exception e) {
			
			response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_MERCADO_MODIFICADO_EXCEP);
			e.getStackTrace();
			
			ResponseEntity<MercadoResponseRest> respError = new ResponseEntity<MercadoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			return respError;
			
		}
		
		ResponseEntity<MercadoResponseRest> respuesta = new ResponseEntity<MercadoResponseRest>(response, HttpStatus.OK);
		
		return respuesta;
		
	}

	/**
	 * Eliminar mercado
	 */
	@Override
	@Transactional
	public ResponseEntity<MercadoResponseRest> deleteById(Long id) {
		// TODO Auto-generated method stub

		MercadoResponseRest response = new MercadoResponseRest();
		
		try {
			
			mercadoRepo.deleteById(id);
			
			response.setMetadata(TIPO_RESPUESTA_OK, CODIGO_RESPUESTA_OK, DESC_MERCADO_ELIMINADO_OK);
			
		} catch (Exception e) {
			
			response.setMetadata(TIPO_RESPUESTA_NOK, CODIGO_RESPUESTA_NOK, DESC_MERCADO_ELIMINADO_EXCEP);
			e.getStackTrace();
			
			ResponseEntity<MercadoResponseRest> respError = new ResponseEntity<MercadoResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			return respError;
			
		}
		
		ResponseEntity<MercadoResponseRest> respuesta = new ResponseEntity<MercadoResponseRest>(response, HttpStatus.OK);
		
		return respuesta;
		
	}

}
