package com.universo.company.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.universo.company.entity.Mercado;

@Repository
public interface IMercadoRepo extends CrudRepository<Mercado, Long> {
 
}
