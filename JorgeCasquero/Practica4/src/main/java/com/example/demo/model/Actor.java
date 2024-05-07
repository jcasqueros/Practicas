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

//	@ManyToMany
//	@JoinTable(name = "actor_serie", joinColumns = @JoinColumn(name = "id_actor"), inverseJoinColumns = @JoinColumn(name = "id_serie"))
//	private List<Serie> series;

//	@ManyToMany
//	@JoinTable(name = "actor_pelicula", joinColumns = @JoinColumn(name = "id_actor"), inverseJoinColumns = @JoinColumn(name = "id_pelicula"))
//	private List<Pelicula> peliculas;

}
