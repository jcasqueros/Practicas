package com.viewnext.jorge.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "distritos_viviendas")
public class DistritoViviendas {

	private String distrito;

	private Long numViviendas;

}
