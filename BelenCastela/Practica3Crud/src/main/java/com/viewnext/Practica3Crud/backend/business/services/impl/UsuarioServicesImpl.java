package com.viewnext.Practica3Crud.backend.business.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viewnext.Practica3Crud.backend.business.bo.UsuarioBo;
import com.viewnext.Practica3Crud.backend.business.model.Usuario;
import com.viewnext.Practica3Crud.backend.business.services.UsuarioServices;
import com.viewnext.Practica3Crud.backend.repository.UsuarioRepository;
import com.viewnext.Practica3Crud.backend.repository.custom.UsuarioCustomRepository;
import com.viewnext.Practica3Crud.util.converter.Bo.BoToEntity;
import com.viewnext.Practica3Crud.util.converter.Bo.EntityToBo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioServicesImpl implements UsuarioServices{

	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioCustomRepository usuarioCustomRepository;
	
	@Autowired
	EntityToBo entityToBo;
	
	@Autowired
	BoToEntity boToEntity;

	@Override
	public UsuarioBo create(UsuarioBo usuarioBo) {
		return entityToBo.usuarioToBo(usuarioRepository.save(boToEntity.usuarioBoTousuario(usuarioBo)));
	}

	@Override
	public UsuarioBo read(String mDni) {
		return entityToBo.usuarioToBo(usuarioRepository.findBymDni(mDni)
				.orElseThrow(() -> new EntityNotFoundException("DNI no encontrado")));
	}

	@Override
	public List<UsuarioBo> getAll() {
		List<Usuario> usuarioList = usuarioRepository.findAll();
		List<UsuarioBo> usuarioBoList = new ArrayList<>();
		usuarioList.forEach((usuario) -> usuarioBoList.add(entityToBo.usuarioToBo(usuario)));
		return usuarioBoList;
	}

	@Override
	public UsuarioBo updateUsuario(UsuarioBo usuarioBo) {
		return entityToBo.usuarioToBo(usuarioRepository.save(boToEntity.usuarioBoTousuario(usuarioBo)));
	}

	@Override
	public void delete(String mDni) {
		usuarioRepository.deleteById(mDni);
	}
	
	public List<UsuarioBo> getUsersByAgeNameCriteria (List<Integer> edades, List<String> nombres) {
		try {
			List<Usuario> usuarios = usuarioCustomRepository.getUsersByAgeName(edades, nombres);
			return usuarios.stream().map(entityToBo::usuarioToBo)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new ServiceException("Error al obtener la lista de usuarios filtrados con Criteria Builder.", e);
		}
	}
}
