package com.viewnext.Practica4.backend.business.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity (name="series")
public class Serie implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	Long id;
	String titulo;
	int anho;
	
	@ManyToOne
	@JoinColumn(name = "id_director")
	Director idDirector;
	
	@ManyToOne
	@JoinColumn(name = "id_productora")
	Productora idProductora;
	
	@ManyToMany (fetch = FetchType.EAGER)
	@JoinTable (name = "seriesActores", joinColumns = @JoinColumn(name= "seriesId"))
	List<Actor> actores;
}
