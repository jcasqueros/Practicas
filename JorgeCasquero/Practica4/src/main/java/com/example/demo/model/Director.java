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
public class Director {

	@Id
	@Column(name = "id_director")
	private long idDirector;
	private String nombre;
	private int edad;
	private String nacionalidad;

}
