package com.viewnext.Practica4.util.converter.bo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viewnext.Practica4.backend.business.bo.ActorBo;
import com.viewnext.Practica4.backend.business.bo.DirectorBo;
import com.viewnext.Practica4.backend.business.bo.PeliculaBo;
import com.viewnext.Practica4.backend.business.bo.ProductoraBo;
import com.viewnext.Practica4.backend.business.bo.SerieBo;
import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.business.model.Director;
import com.viewnext.Practica4.backend.business.model.Pelicula;
import com.viewnext.Practica4.backend.business.model.Productora;
import com.viewnext.Practica4.backend.business.model.Serie;

@Component
public class BoToEntity {

	@Autowired
	ModelMapper mapper;
	
	public Actor actorBoToActor(ActorBo actorBo) {
        Actor actor = mapper.map(actorBo, Actor.class);
        return actor;
    }

    public Director directorBoToDirector(DirectorBo directorBo) {
        Director director = mapper.map(directorBo, Director.class);
        return director;
    }
    
    public Pelicula peliculaBoToPelicula(PeliculaBo peliculaBo) {
        Pelicula pelicula = mapper.map(peliculaBo, Pelicula.class);
        return pelicula;
    }

    public Productora productoraBoToProductora(ProductoraBo productoraBo) {
        Productora productora = mapper.map(productoraBo, Productora.class);
        return productora;
    }

    public Serie serieBoToSerie(SerieBo serieBo) {
        Serie serie = mapper.map(serieBo, Serie.class);
        return serie;
    }
}
