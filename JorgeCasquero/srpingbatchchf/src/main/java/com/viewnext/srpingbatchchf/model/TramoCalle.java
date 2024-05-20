package com.viewnext.srpingbatchchf.model;


import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TramoCalle {

	// CODIGO_CALLE,TIPO_VIA,NOMBRE_CALLE,PRIMER_NUM_TRAMO,ULTIMO_NUM_TRAMO,BARRIO,COD_DISTRITO,NOM_DISTRITO
	@Id
	private long codigoCalle;
	private String tipoVia;
	private String nombreCalle;
	private int primerNumTramo;
	private int ultimoNumTramo;
	private int barrio;
	private int codDistrito;
	private String nomDistrito;

}
