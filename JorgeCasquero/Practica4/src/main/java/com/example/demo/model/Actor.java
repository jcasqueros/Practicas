package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Actor {

	@Id
	@Column(name = "id_actor")
	private long idActor;
	private String nombre;
	private int edad;
	private String nacionalidad;
}
