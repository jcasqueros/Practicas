package com.viewnext.Practica4.util.converter.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viewnext.Practica4.backend.business.bo.ActorBo;
import com.viewnext.Practica4.backend.business.bo.DirectorBo;
import com.viewnext.Practica4.backend.business.bo.PeliculaBo;
import com.viewnext.Practica4.backend.business.bo.ProductoraBo;
import com.viewnext.Practica4.backend.business.bo.SerieBo;
import com.viewnext.Practica4.backend.presentation.dto.ActorDto;
import com.viewnext.Practica4.backend.presentation.dto.DirectorDto;
import com.viewnext.Practica4.backend.presentation.dto.PeliculaDto;
import com.viewnext.Practica4.backend.presentation.dto.ProductoraDto;
import com.viewnext.Practica4.backend.presentation.dto.SerieDto;

@Component
public class DtoToBo {

	@Autowired
	ModelMapper mapper;
	
	public ActorBo actorDtoToBo(ActorDto actorDto) {
        ActorBo actorBo = mapper.map(actorDto, ActorBo.class);
        return actorBo;
    }
    
	public DirectorBo directorDtoToBo(DirectorDto directorDto) {
	    DirectorBo directorBo = mapper.map(directorDto, DirectorBo.class);
	    return directorBo;
	}

	public ProductoraBo productoraDtoToBo(ProductoraDto productoraDto) {
	    ProductoraBo productoraBo = mapper.map(productoraDto, ProductoraBo.class);
	    return productoraBo;
	}

	public SerieBo serieDtoToBo(SerieDto serieDto) {
	    SerieBo serieBo = mapper.map(serieDto, SerieBo.class);
	    return serieBo;
	}

	public PeliculaBo peliculaDtoToBo(PeliculaDto peliculaDto) {
	    PeliculaBo peliculaBo = mapper.map(peliculaDto, PeliculaBo.class);
	    return peliculaBo;
	}

    
}
