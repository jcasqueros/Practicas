package com.viewnext.Practica3Crud.backend.presentation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viewnext.Practica3Crud.backend.business.bo.UsuarioBo;
import com.viewnext.Practica3Crud.backend.business.services.UsuarioServices;
import com.viewnext.Practica3Crud.backend.presentation.dto.UsuarioDto;
import com.viewnext.Practica3Crud.util.converter.Dto.BoToDto;
import com.viewnext.Practica3Crud.util.converter.Dto.DtoToBo;

@RestController
@RequestMapping("/users")
public class UsuarioController {

	@Autowired
	DtoToBo dtoToBo;

	@Autowired
	BoToDto boToDto;

	@Autowired
	UsuarioServices usuarioServices;

	@PostMapping("/create")
	public ResponseEntity<UsuarioDto> createUsuario(@RequestBody UsuarioDto usuarioDto) {
		try {
			UsuarioBo usuarioBo = dtoToBo.usuarioDtoToBo(usuarioDto);
			usuarioServices.create(usuarioBo);
			UsuarioDto savedUsuarioDto = boToDto.usuarioBoTousuarioDto(usuarioBo);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuarioDto);
		} catch (ServiceException se) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/all")
	public ResponseEntity<List<UsuarioDto>> getUsuarios() {
		try {
			List<UsuarioBo> usuarioBo = usuarioServices.getAll();
			System.out.println(usuarioBo);
			List<UsuarioDto> usuarioDto = usuarioBo.stream().map(boToDto::usuarioBoTousuarioDto)
					.collect(Collectors.toList());
			System.out.println(usuarioDto);
			return ResponseEntity.ok(usuarioDto);
		} catch (ServiceException se) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/{mDni}")
	public ResponseEntity<UsuarioDto> getUsuarioByDni(@PathVariable String mDni) {
		try {
			UsuarioBo usuarioBo = usuarioServices.read(mDni);
			UsuarioDto usuarioDto = boToDto.usuarioBoTousuarioDto(usuarioBo);
			return ResponseEntity.ok(usuarioDto);
		} catch (ServiceException se) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (NullPointerException npe) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping("/{mDni}")
	public ResponseEntity<UsuarioDto> updateUsuario(@PathVariable String mDni, @RequestBody UsuarioDto usuarioDto) {
		try {
			UsuarioBo usuarioBo = dtoToBo.usuarioDtoToBo(usuarioDto);
			usuarioBo = usuarioServices.read(mDni);
			usuarioBo.setMAge(usuarioDto.getMAge());
			usuarioBo.setMDni(usuarioDto.getMDni());
			usuarioBo.setMName(usuarioDto.getMName());
			usuarioBo.setMSurname(usuarioDto.getMSurname());
			usuarioServices.updateUsuario(usuarioBo);
			UsuarioDto updateUsuarioDto = boToDto.usuarioBoTousuarioDto(usuarioBo);
			return ResponseEntity.status(HttpStatus.CREATED).body(updateUsuarioDto);
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (NullPointerException npe) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/{mDni}")
	public ResponseEntity<Void> deleteUsuario(@PathVariable String mDni) {
		try {
			usuarioServices.delete(mDni);
			return ResponseEntity.ok().build();
		} catch (ServiceException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (NullPointerException npe) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	//localhost:8080/users/filter?edades=11,33&nombres=Miguel,Pedro
	@GetMapping("/filter")
	public ResponseEntity<List<UsuarioDto>> getUsersByAgeNameCriteria
	(@RequestParam(required = false) List<Integer> edades,
			@RequestParam(required = false) List<String> nombres) {
		try {
			List<UsuarioBo> users = usuarioServices.getUsersByAgeNameCriteria(edades, nombres);
			List<UsuarioDto> usersDto = users.stream().map(boToDto::usuarioBoTousuarioDto)
					.collect(Collectors.toList());
			return ResponseEntity.ok(usersDto);
		} catch (ServiceException se) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
