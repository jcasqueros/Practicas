package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
	@Column(name = "id_pelicula")
	private long idPelicula;

	private String titulo;

	private int anio;

	@ManyToOne
	@JoinColumn(name = "id_dir")
	private Director director;

	@OneToOne
	@JoinColumn(name = "id_pro")
	private Productora productora;

	@ManyToMany
	@JoinTable(name = "actor_pelicula", joinColumns = @JoinColumn(name = "id_pelicula"), inverseJoinColumns = @JoinColumn(name = "id_actor"))
	private List<Actor> actor;
}
