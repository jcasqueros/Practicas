package com.viewnext.jorge.values;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class Values {

	/**ruta del archivo
	 * 
	 * cogemos el valor del properties
	 */
	@Value("${file_input}")
	private String fileInput;

	/**valor del distrito
	 * cogemos el valro del properties
	 */
	@Value("${distrito_especifico}")
	private String distritoEspecifico;

	/** url de la bd de mongo
	 * valor que se inyecta desde el properties
	 */
	@Value("${mongo_url}")
	private String mongoUrl;

	/**
	 * 
	 */
	@Value("${distritos_restantes}")
	private String distritosRestantes;
}
