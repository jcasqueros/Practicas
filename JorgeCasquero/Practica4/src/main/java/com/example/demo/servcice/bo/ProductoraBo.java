package com.example.demo.servcice.bo;

import java.util.List;

import com.example.demo.model.Pelicula;
import com.example.demo.model.Serie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductoraBo {

	
	private long idProductora;
	private String nombre;
	private int anioFundacion;
    private List<Serie> series;
    private List<Pelicula> peliculas;
}
