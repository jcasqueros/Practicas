package com.example.demo.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Actor;
import com.example.demo.model.Director;
import com.example.demo.model.Pelicula;
import com.example.demo.model.Productora;
import com.example.demo.model.Serie;
import com.example.demo.servcice.bo.ActorBo;
import com.example.demo.servcice.bo.DirectorBo;
import com.example.demo.servcice.bo.PeliculaBo;
import com.example.demo.servcice.bo.ProductoraBo;
import com.example.demo.servcice.bo.SerieBo;

@Component
public class ModelToBo {
	@Autowired
	ModelMapper modelMapper;

	public ActorBo actorToActorBo(Actor actor) {
		return modelMapper.map(actor, ActorBo.class);
	}

	public DirectorBo directorToDirectorBo(Director director) {
		return modelMapper.map(director, DirectorBo.class);
	}

	public ProductoraBo productoraToProductoraBo(Productora productora) {
		return modelMapper.map(productora, ProductoraBo.class);
	}

	public PeliculaBo peliculaToPeliculaBo(Pelicula pelicula) {
		return modelMapper.map(pelicula, PeliculaBo.class);
	}

	public SerieBo serieToSerieBo(Serie serie) {
		return modelMapper.map(serie, SerieBo.class);
	}

}
