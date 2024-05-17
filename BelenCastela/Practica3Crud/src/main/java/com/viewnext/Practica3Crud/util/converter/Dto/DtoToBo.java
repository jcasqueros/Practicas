package com.viewnext.Practica3Crud.util.converter.Dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.viewnext.Practica3Crud.backend.business.bo.UsuarioBo;
import com.viewnext.Practica3Crud.backend.presentation.dto.UsuarioDto;

@Component
public class DtoToBo {

	@Autowired
	ModelMapper mapper;
	
	public UsuarioBo usuarioDtoToBo (UsuarioDto usuarioDto) {
		UsuarioBo usuarioBo = mapper.map(usuarioDto, UsuarioBo.class);
		return usuarioBo;
	}
}
