package com.universo.company.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
//@EqualsAndHashCode
@ToString
public class MercadoResponseRest extends ResponseRest {

	private MercadoResponse mercadoResponse = new MercadoResponse();
	
}
