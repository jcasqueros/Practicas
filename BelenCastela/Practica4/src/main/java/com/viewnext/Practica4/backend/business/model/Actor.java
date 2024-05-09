package com.viewnext.Practica4.backend.business.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="actores")
public class Actor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	Long id;
	String nombre;
	int edad;
	String nacionalidad;
}
