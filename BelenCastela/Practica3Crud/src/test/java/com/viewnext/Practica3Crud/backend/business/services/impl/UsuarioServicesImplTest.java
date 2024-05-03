package com.viewnext.Practica3Crud.backend.business.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.viewnext.Practica3Crud.backend.business.bo.UsuarioBo;
import com.viewnext.Practica3Crud.backend.business.model.Usuario;
import com.viewnext.Practica3Crud.backend.repository.UsuarioRepository;
import com.viewnext.Practica3Crud.util.converter.Bo.BoToEntity;
import com.viewnext.Practica3Crud.util.converter.Bo.EntityToBo;

@ExtendWith(MockitoExtension.class)
class UsuarioServicesImplTest {
	
	private Usuario usuario, usuario2;
	private UsuarioBo usuarioBo, usuarioBo2;
	
	@Mock
	UsuarioRepository usuarioRepository;
	
	@Mock
	EntityToBo entityToBo;
	
	@Mock
	BoToEntity boToEntity;
	
	@InjectMocks
	UsuarioServicesImpl usuarioServicesImpl;
	
	@BeforeEach
	public void init() {
		initMocks();
	}
	
	private void initMocks() {
		usuario = new Usuario();
		usuario.setMAge(1);
		usuario.setMDni("000A");
		usuario.setMName("User1");
		usuario.setMSurname("User1Surname");
		
		usuarioBo = new UsuarioBo();
		usuarioBo.setMAge(1);
		usuarioBo.setMDni("000A");
		usuarioBo.setMName("User1");
		usuarioBo.setMSurname("User1Surname");
		
		usuario2 = new Usuario();
		usuario2.setMAge(2);
		usuario2.setMDni("111B");
		usuario2.setMName("User2");
		usuario2.setMSurname("User2Surname");
		
		usuarioBo2 = new UsuarioBo();
		usuarioBo2.setMAge(2);
		usuarioBo2.setMDni("111B");
		usuarioBo2.setMName("User2");
		usuarioBo2.setMSurname("User2Surname");
	}

	@Test
	void testCreate() {
		when(usuarioRepository.save(usuario2)).thenReturn(usuario2);
		when(entityToBo.usuarioToBo(usuario2)).thenReturn(usuarioBo2);
		when(boToEntity.usuarioBoTousuario(usuarioBo2)).thenReturn(usuario2);
		
		UsuarioBo usuarioBoTest = usuarioServicesImpl.create(usuarioBo2);
		
		assertNotNull(usuarioBoTest);
		assertEquals("111B", usuarioBoTest.getMDni());
		assertEquals("User2", usuarioBoTest.getMName());
		assertEquals("User2Surname", usuarioBoTest.getMSurname());
		assertEquals(2, usuarioBoTest.getMAge());
	}

	@Test
	void testRead() {
		when(usuarioRepository.findBymDni("000A")).thenReturn(Optional.of(usuario));
		when(entityToBo.usuarioToBo(usuario)).thenReturn(usuarioBo);
		
		System.out.println(usuario);
		
		UsuarioBo usuarioBo1 = usuarioServicesImpl.read("000A");
		System.out.println("Usuario BO 2: " + usuarioBo1);
		
		assertEquals("000A", usuarioBo1.getMDni());
		assertEquals("User1", usuarioBo1.getMName());
		assertEquals("User1Surname", usuarioBo1.getMSurname());
		assertEquals(1, usuarioBo1.getMAge());
	}

	@Test
	void testGetAll() {
		when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario, usuario2));
		when(entityToBo.usuarioToBo(usuario)).thenReturn(usuarioBo);
		when(entityToBo.usuarioToBo(usuario2)).thenReturn(usuarioBo2);
		
		List<UsuarioBo> listaUsuarioBo = usuarioServicesImpl.getAll();
		
		assertNotNull(listaUsuarioBo);
		assertEquals(2, listaUsuarioBo.size());
		assertTrue(listaUsuarioBo.contains(usuarioBo));
		assertTrue(listaUsuarioBo.contains(usuarioBo2));
	}

	@Test
	void testUpdateUsuario() {
		when(usuarioRepository.save(usuario2)).thenReturn(usuario2);
		when(entityToBo.usuarioToBo(usuario2)).thenReturn(usuarioBo2);
		when(boToEntity.usuarioBoTousuario(usuarioBo2)).thenReturn(usuario2);
		
		UsuarioBo usuarioBoTest = usuarioServicesImpl.updateUsuario(usuarioBo2);
		
		assertNotNull(usuarioBoTest);
		assertEquals("111B", usuarioBoTest.getMDni());
		assertEquals("User2", usuarioBoTest.getMName());
		assertEquals("User2Surname", usuarioBoTest.getMSurname());
		assertEquals(2, usuarioBoTest.getMAge());
	}


	@Test
	void testDelete() {
		usuarioServicesImpl.delete("111B");
	}

}
