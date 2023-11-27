package com.universo.company.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.universo.company.entity.Empresa;

@Repository
public interface IEmpresaRepo extends CrudRepository<Empresa, Long> {

}
