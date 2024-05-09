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
public class BoToDto {

	@Autowired
	ModelMapper mapper;

	public ActorDto actorBoToActorDto(ActorBo actorBo) {
		ActorDto actorDto = mapper.map(actorBo, ActorDto.class);
		return actorDto;
	}
	
	public DirectorDto directorBoToDirectorDto(DirectorBo directorBo) {
        DirectorDto directorDto = mapper.map(directorBo, DirectorDto.class);
        return directorDto;
    }

    public ProductoraDto productoraBoToProductoraDto(ProductoraBo productoraBo) {
        ProductoraDto productoraDto = mapper.map(productoraBo, ProductoraDto.class);
        return productoraDto;
    }

    public SerieDto serieBoToSerieDto(SerieBo serieBo) {
        SerieDto serieDto = mapper.map(serieBo, SerieDto.class);
        return serieDto;
    }

    public PeliculaDto peliculaBoToPeliculaDto(PeliculaBo peliculaBo) {
        PeliculaDto peliculaDto = mapper.map(peliculaBo, PeliculaDto.class);
        return peliculaDto;
    }
	
}