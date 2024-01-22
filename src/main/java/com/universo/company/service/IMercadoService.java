package com.universo.company.service;

import org.springframework.http.ResponseEntity;

import com.universo.company.entity.Mercado;
import com.universo.company.response.MercadoResponseRest;

public interface IMercadoService {

	public ResponseEntity<MercadoResponseRest> search();
	
	public ResponseEntity<MercadoResponseRest> searchById(Long id);
	
	public ResponseEntity<MercadoResponseRest> searchByClave (String cadena);
	
	public ResponseEntity<MercadoResponseRest> save(Mercado mercado);
	
	public ResponseEntity<MercadoResponseRest> update(Mercado mercado, Long id); 
	
	public ResponseEntity<MercadoResponseRest> deleteById(Long id);
	
}
