package com.viewnext.Practica4.backend.business.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name= "productoras")
public class Productora implements Serializable{
	
	private static final long serialVersionUID= 1L;

	@Id
	Long id;
	String nombre;
	int anhoFundacion;
}
