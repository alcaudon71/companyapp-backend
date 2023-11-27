package com.universo.company.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="empresa")
@Data
public class Empresa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2286041983568239156L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Column(name="clave")
	String clave;
	@Column(name="descripcion")
	String descripcion;
	@Column(name="capital")
	Integer capital;   // millones de euros
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JsonIgnoreProperties ( {"hibernateLazyInitializater", "handler"} )
	Mercado mercado;
	
	@Lob
	@Basic (fetch = FetchType.LAZY)
	@Column(name="imagen", columnDefinition="longblob")
	private byte[] imagen;
	
}
