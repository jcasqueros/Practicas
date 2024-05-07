package com.example.demo.servcice.bo;

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
public class SerieBo {

	private long idSerie;
	private String titulo;
	private int anio;
	private Director director;
	private Productora productora;
	private List<Actor> actores;

}
