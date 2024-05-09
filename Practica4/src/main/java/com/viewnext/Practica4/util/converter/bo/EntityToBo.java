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
public class EntityToBo {
    @Autowired
    ModelMapper mapper;
    
    public ActorBo actorToActorBo(Actor actor) {
        ActorBo actorBo = mapper.map(actor, ActorBo.class);
        return actorBo;
    }

    public DirectorBo directorToDirectorBo(Director director) {
        DirectorBo directorBo = mapper.map(director, DirectorBo.class);
        return directorBo;
    }
    
    public PeliculaBo peliculaToPeliculaBo(Pelicula pelicula) {
        PeliculaBo peliculaBo = mapper.map(pelicula, PeliculaBo.class);
        return peliculaBo;
    }

    public ProductoraBo productoraToProductoraBo(Productora productora) {
        ProductoraBo productoraBo = mapper.map(productora, ProductoraBo.class);
        return productoraBo;
    }

    public SerieBo serieToSerieBo(Serie serie) {
        SerieBo serieBo = mapper.map(serie, SerieBo.class);
        return serieBo;
    }
}
