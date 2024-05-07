package com.example.demo.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.presentation.dto.ActorDto;
import com.example.demo.presentation.dto.DirectorDto;
import com.example.demo.presentation.dto.PeliculaDto;
import com.example.demo.presentation.dto.ProductoraDto;
import com.example.demo.presentation.dto.SerieDto;
import com.example.demo.servcice.bo.ActorBo;
import com.example.demo.servcice.bo.DirectorBo;
import com.example.demo.servcice.bo.PeliculaBo;
import com.example.demo.servcice.bo.ProductoraBo;
import com.example.demo.servcice.bo.SerieBo;

@Component
public class BoToDTo {

	@Autowired
	ModelMapper modelMapper;

	public ActorDto boToActorDto(ActorBo actorBo) {
		return modelMapper.map(actorBo, ActorDto.class);
	}

	public DirectorDto boToDirectorDto(DirectorBo directorBo) {
		return modelMapper.map(directorBo, DirectorDto.class);
	}

	public ProductoraDto boToProductoraDto(ProductoraBo productoraBo) {
		return modelMapper.map(productoraBo, ProductoraDto.class);
	}

	public PeliculaDto boToPeliculaDto(PeliculaBo peliculaBo) {
		return modelMapper.map(peliculaBo, PeliculaDto.class);
	}

	public SerieDto boToSerieDto(SerieBo serieBo) {
		return modelMapper.map(serieBo, SerieDto.class);
	}

}
