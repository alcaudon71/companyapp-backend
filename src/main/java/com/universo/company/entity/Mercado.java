package com.universo.company.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="mercado")
@Data
public class Mercado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -391266388642843275L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(name="clave")
	String clave;
	@Column(name="descripcion")
	String descripcion;
	@Column(name="sector")
	String sector;
	@Column(name="pais")
	String pais;
	
}
