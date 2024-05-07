package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Serie {

	@Id
	@Column(name = "id_serie")
	private long idSerie;

	private String titulo;

	private int anio;

	@ManyToOne
	@JoinColumn(name = "id_dir")
	private Director director;

	@ManyToOne
	@JoinColumn(name = "id_pro")
	private Productora productora;

	@ManyToMany
	@JoinTable(name = "actor_serie", joinColumns = @JoinColumn(name = "id_serie"), inverseJoinColumns = @JoinColumn(name = "id_actor"))
	private List<Actor> actores;

}