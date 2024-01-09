package com.universo.company.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.universo.company.entity.Empresa;

@Repository
public interface IEmpresaRepo extends CrudRepository<Empresa, Long> {

	// Buscar empresas cuyo nombre contengan un determinado string
	@Query("select e from Empresa e where e.descripcion like %?1 ")
	List<Empresa> findByDescripcionLike (String cadena);
	
	// Buscar todas las empresas cuyo nombre contenga la cadena enviada
	// Metodo en pseudocodigo Spring equivalente al anterior
	// Docs ---> https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	List<Empresa> findByDescripcionContainingIgnoreCase (String cadena);
	
}
