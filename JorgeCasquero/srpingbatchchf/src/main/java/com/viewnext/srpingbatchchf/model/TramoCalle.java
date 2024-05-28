package com.viewnext.srpingbatchchf.model;

import javax.persistence.Id;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Document(collection="tramo")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TramoCalle {

	// CODIGO_CALLE,TIPO_VIA,NOMBRE_CALLE,PRIMER_NUM_TRAMO,ULTIMO_NUM_TRAMO,BARRIO,COD_DISTRITO,NOM_DISTRITO
	@Id
	private ObjectId id;
	
	private long codigoCalle;
	private String tipoVia;
	private String nombreCalle;
	private int primerNumTramo;
	private int ultimoNumTramo;
	private String barrio;
	private int codDistrito;
	private String nomDistrito;

}
