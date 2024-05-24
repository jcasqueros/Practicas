package com.viewnext.jorge.model;


import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Document(collection = "calle")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Calle{

	// CODIGO_CALLE,TIPO_VIA,NOMBRE_CALLE,PRIMER_NUM_TRAMO,ULTIMO_NUM_TRAMO,BARRIO,COD_DISTRITO,NOM_DISTRITO
	@Id
	@Field("tipo_via")
	private String tipoVia;
	
	@Field("nombre_calle")
	private String nombreCalle;
	
	@Field("primer_num_tramo")
	private int primerNumTramo;
	
	@Field("ultimo_num_tramo")
	private int ultimoNumTramo;
	
	@Field("barrio")
	private String barrio;
	
	@Field("cod_distrito")
	private int codDistrito;
	
	@Field("nom_distrito")
	private String nomDistrito;

}
