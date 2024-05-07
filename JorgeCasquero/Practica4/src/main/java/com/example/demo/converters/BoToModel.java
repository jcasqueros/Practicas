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
public class BoToModel {
	@Autowired
	ModelMapper modelMapper;

	public Actor boToActor(ActorBo actorBo) {
		return modelMapper.map(actorBo, Actor.class);
	}

	public Director boToDirector(DirectorBo directorBo) {
		return modelMapper.map(directorBo, Director.class);
	}

	public Productora boToProductora(ProductoraBo productoraBo) {
		return modelMapper.map(productoraBo, Productora.class);
	}

	public Pelicula boToPelicula(PeliculaBo peliculaBo) {
		return modelMapper.map(peliculaBo, Pelicula.class);
	}

	public Serie boToSerie(SerieBo serieBo) {
		return modelMapper.map(serieBo, Serie.class);
	}

}
