package com.universo.company.response;

import java.util.List;

import com.universo.company.entity.Empresa;

import lombok.Data;

@Data
public class EmpresaResponse {

	private List<Empresa> empresa;
	
}
