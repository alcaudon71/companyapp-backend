package com.universo.company.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.universo.company.entity.Empresa;
import com.universo.company.response.EmpresaResponseRest;
import com.universo.company.service.IEmpresaService;

@Service 
public class EmpresaServiceImpl implements IEmpresaService {

	@Override
	public ResponseEntity<EmpresaResponseRest> save(Empresa empresa, Long mercadoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<EmpresaResponseRest> searchById(Long empresaId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<EmpresaResponseRest> searchByName(String cadena) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<EmpresaResponseRest> deleteById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<EmpresaResponseRest> searchAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<EmpresaResponseRest> update(Empresa empresa, Long mercadoId, Long empresaId) {
		// TODO Auto-generated method stub
		return null;
	}

}
