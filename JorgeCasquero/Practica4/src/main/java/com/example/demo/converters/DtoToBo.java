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
public class DtoToBo {
	
	@Autowired
	ModelMapper modelMapper;

	public ActorBo dtoToActorBo(ActorDto actorDto) {
		return modelMapper.map(actorDto, ActorBo.class);
	}

	public DirectorBo dtoToDirectorBo(DirectorDto directorDto) {
		return modelMapper.map(directorDto, DirectorBo.class);
	}

	public ProductoraBo dtoToProductoraBo(ProductoraDto productoraDto) {
		return modelMapper.map(productoraDto, ProductoraBo.class);
	}

	public PeliculaBo dtoToPeliculaBo(PeliculaDto peliculaDto) {
		return modelMapper.map(peliculaDto, PeliculaBo.class);
	}

	public SerieBo dtoToSerieBo(SerieDto serieDto) {
		return modelMapper.map(serieDto, SerieBo.class);
	}


}
