package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Actor {

	@Id
	private long idActor;
	private String nombre;
	private int edad;
	private String nacionalidad;

//	@ManyToOne
//	@JoinColumn(name = "id_serie")
//	private Serie serie;
}
