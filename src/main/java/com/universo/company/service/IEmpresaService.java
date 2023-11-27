package com.universo.company.service;

import org.springframework.http.ResponseEntity;

import com.universo.company.entity.Empresa;
import com.universo.company.response.EmpresaResponseRest;

public interface IEmpresaService {

	public ResponseEntity<EmpresaResponseRest> save(Empresa empresa, Long mercadoId);
	
	public ResponseEntity<EmpresaResponseRest> searchById (Long empresaId);
	
	public ResponseEntity<EmpresaResponseRest> searchByName (String cadena);
	
	public ResponseEntity<EmpresaResponseRest> deleteById (Long id);
	
	public ResponseEntity<EmpresaResponseRest> searchAll ();
	
	public ResponseEntity<EmpresaResponseRest> update(Empresa empresa, Long mercadoId, Long empresaId);
	
}
