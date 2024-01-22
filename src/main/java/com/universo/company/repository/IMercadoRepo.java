package com.universo.company.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.universo.company.entity.Mercado;

@Repository
public interface IMercadoRepo extends CrudRepository<Mercado, Long> {

	// Buscar todos los mercados cuya clave contenga la cadena enviada
	// Metodo en pseudocodigo sin SQL 
	// Docs ---> https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	List<Mercado> findByClaveContainingIgnoreCase (String cadena);
	
}
