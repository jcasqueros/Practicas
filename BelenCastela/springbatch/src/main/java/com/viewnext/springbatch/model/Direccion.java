package com.viewnext.springbatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity (name="direcciones")
public class Direccion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int codigoCalle;
    private String tipoVia;
    private String nombreCalle;
    private int primerNumTramo;
    private int ultimoNumTramo;
    private String barrio;
    private int codDistrito;
    private String nomDistrito;
}
