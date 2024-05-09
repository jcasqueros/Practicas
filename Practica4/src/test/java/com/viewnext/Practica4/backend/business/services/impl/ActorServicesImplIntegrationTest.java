package com.viewnext.Practica4.backend.business.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.viewnext.Practica4.backend.business.bo.ActorBo;
import com.viewnext.Practica4.backend.business.model.Actor;
import com.viewnext.Practica4.backend.repository.ActorRepository;
import com.viewnext.Practica4.backend.repository.custom.ActorCustomRepository;
import com.viewnext.Practica4.util.converter.bo.BoToEntity;
import com.viewnext.Practica4.util.converter.bo.EntityToBo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ActorServicesImplIntegrationTest {

    @InjectMocks
    private ActorServicesImpl actorServices;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private ActorCustomRepository actorCustomRepository;

    @Mock
    private EntityToBo entityToBo;

    @Mock
    private BoToEntity boToEntity;

    private Actor actor1;
    private Actor actor2;
    private ActorBo actorBo1;
    private ActorBo actorBo2;

    @BeforeEach
    public void init() {
        initMocks();
    }

    private void initMocks() {
        actor1 = new Actor();
        actor1.setId(1L);
        actor1.setNombre("Actor 1");
        actor1.setEdad(30);
        actor1.setNacionalidad("Española");

        actor2 = new Actor();
        actor2.setId(2L);
        actor2.setNombre("Actor 2");
        actor2.setEdad(35);
        actor2.setNacionalidad("Francesa");

        actorBo1 = new ActorBo();
        actorBo1.setId(1L);
        actorBo1.setNombre("Actor 1");
        actorBo1.setEdad(30);
        actorBo1.setNacionalidad("Española");

        actorBo2 = new ActorBo();
        actorBo2.setId(2L);
        actorBo2.setNombre("Actor 2");
        actorBo2.setEdad(35);
        actorBo2.setNacionalidad("Francesa");
    }

    @Test
    public void testCreate() {
        when(boToEntity.actorBoToActor(actorBo1)).thenReturn(actor1);
        when(actorRepository.save(actor1)).thenReturn(actor1);
        when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

        ActorBo createdActor = actorServices.create(actorBo1);

        assertEquals(actorBo1, createdActor);
    }

    @Test
    public void testRead() {
        when(actorRepository.findById(1L)).thenReturn(Optional.of(actor1));
        when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

        ActorBo readActor = actorServices.read(1L);

        assertEquals(actorBo1, readActor);
    }

    @Test
    public void testGetAll() {
        when(actorRepository.findAll()).thenReturn(Arrays.asList(actor1, actor2));
        when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);
        when(entityToBo.actorToActorBo(actor2)).thenReturn(actorBo2);

        List<ActorBo> actors = actorServices.getAll();

        assertEquals(Arrays.asList(actorBo1, actorBo2), actors);
    }

    @Test
    public void testDelete() {
        when(actorRepository.existsById(1L)).thenReturn(true);
        doNothing().when(actorRepository).deleteById(1L);

        actorServices.delete(1L);

        verify(actorRepository).deleteById(1L);
    }



    @Test
    public void testUpdate() {
        when(boToEntity.actorBoToActor(actorBo1)).thenReturn(actor1);
        when(actorRepository.save(actor1)).thenReturn(actor1);
        when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

        ActorBo updatedActor = actorServices.update(actorBo1);

        assertEquals(actorBo1, updatedActor);
    }

    @Test
    public void testGetActoresCb() {
        when(actorCustomRepository.getActoresCb()).thenReturn(Arrays.asList(actor1, actor2));
        when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);
        when(entityToBo.actorToActorBo(actor2)).thenReturn(actorBo2);

        List<ActorBo> actors = actorServices.getActoresCb();

        assertEquals(Arrays.asList(actorBo1, actorBo2), actors);
    }

    @Test
    public void testCreateCb() {
        when(boToEntity.actorBoToActor(actorBo1)).thenReturn(actor1);
        when(actorCustomRepository.createCb(actor1)).thenReturn(actor1);
        when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

        ActorBo createdActor = actorServices.createCb(actorBo1);

        assertEquals(actorBo1, createdActor);
    }

    @Test
    public void testReadCb() {
        when(actorCustomRepository.readCb(1L)).thenReturn(actor1);
        when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

        ActorBo readActor = actorServices.readCb(1L);

        assertEquals(actorBo1, readActor);
    }

    @Test
    public void testUpdateCb() {
        when(boToEntity.actorBoToActor(actorBo1)).thenReturn(actor1);
        when(actorCustomRepository.updateCb(actor1)).thenReturn(actor1);
        when(entityToBo.actorToActorBo(actor1)).thenReturn(actorBo1);

        ActorBo updatedActor = actorServices.updateCb(actorBo1);

        assertEquals(actorBo1, updatedActor);
    }

    @Test
    public void testDeleteCb() {
        doNothing().when(actorCustomRepository).deleteCb(1L);

        actorServices.deleteCb(1L);

        verify(actorCustomRepository).deleteCb(1L);
    }
}
