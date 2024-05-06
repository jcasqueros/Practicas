package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Pelicula {

	@Id
	private long idPelicula;

	private String titulo;

	private int anio;

	@OneToOne
	@JoinColumn(name = "id_dir")
	private Director director;

	@OneToOne
	@JoinColumn(name = "id_pro")
	private Productora productora;

	@OneToMany
	@JoinColumn(name = "id_actor")
	private List<Actor> actor;

}
