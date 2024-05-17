package com.viewnext.Practica3Crud.util.converter.Dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viewnext.Practica3Crud.backend.business.bo.UsuarioBo;
import com.viewnext.Practica3Crud.backend.presentation.dto.UsuarioDto;

@Component
public class BoToDto {

	@Autowired
	ModelMapper mapper;
	
	public UsuarioDto usuarioBoTousuarioDto (UsuarioBo usuarioBo) {
		UsuarioDto usuarioDto = mapper.map(usuarioBo, UsuarioDto.class);
		return usuarioDto;
	}
}
