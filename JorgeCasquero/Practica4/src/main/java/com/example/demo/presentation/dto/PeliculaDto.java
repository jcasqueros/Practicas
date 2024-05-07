package com.example.demo.presentation.dto;

import java.util.List;

import com.example.demo.model.Actor;
import com.example.demo.model.Director;
import com.example.demo.model.Productora;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor

public class PeliculaDto {
	private long idPelicula;

	private String titulo;

	private int anio;

	private Director director;

	private Productora productora;

	private List<Actor> actor;

}
