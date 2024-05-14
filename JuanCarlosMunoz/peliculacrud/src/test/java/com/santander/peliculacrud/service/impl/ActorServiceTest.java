package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.ActorRepository;
import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.util.mapper.ActorBOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * The type Actor service test.
 */
@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    @InjectMocks
    private ActorService actorService;


    @Mock
    private ActorBOMapper actorBOMapper;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private Logger logger;

    /**
     * The Result.
     */
    @Mock
    BindingResult result;

    private ActorBO actorBO;

    private List<ActorBO> actors;

    private Actor actor;

    private static final long ID = 1;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        actorBO = ActorBO.builder().name("John Doe").age(30).nation("ESP").build();
        actor = Actor.builder().name("John Doe").age(30).nation("ESP").build();

        actors = new ArrayList<>();
        actors.add(ActorBO.builder().name("John Doe").age(30).nation("ESP").build());
        actors.add(ActorBO.builder().name("John Doe 1").age(30).nation("ING").build());
        actors.add(ActorBO.builder().name("John Doe 2").age(30).nation("ESP").build());
        actors.add(ActorBO.builder().name("John Doe 3").age(30).nation("FRA").build());
        actors.add(ActorBO.builder().name("John Doe 4").age(30).nation("ESP").build());
    }

    /**
     * Test create actor valid data.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test create actor valid data")
    void testCreateActor_validData() throws Exception {
        // Configuración de mocks
        when(actorBOMapper.boToEntity(actorBO)).thenReturn(new Actor());
        when(actorRepository.save(any(Actor.class))).thenReturn(new Actor());
        when(actorBOMapper.entityToBo(any(Actor.class))).thenReturn(actorBO);

        // Ejecución del método
        ActorBO result = actorService.createActor(actorBO);

        // Verificaciones
        assertNotNull(result);
        assertEquals(actorBO.getName(), result.getName());
        assertEquals(actorBO.getAge(), result.getAge());
        assertEquals(actorBO.getNation(), result.getNation());

    }

    /**
     * Test create actor repository exception.
     */
    @Test
    @DisplayName("Test create actor repository exception")
    void testCreateActor_repositoryException() {
        // Configuración de mocks
        when(actorBOMapper.boToEntity(actorBO)).thenReturn(new Actor());
        when(actorRepository.save(any(Actor.class))).thenThrow(new RuntimeException("Failed to create actor: "));

        // Ejecución del método y verificación de la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> actorService.createActor(actorBO));
        assertEquals("Failed to create actor: ", exception.getMessage());
    }

    /**
     * Test get all actors.
     */
    @Test
    @DisplayName("Test get all actors")
    void testGetAllActors() {
        // Configuración de mocks
        when(actorRepository.findAll()).thenReturn(List.of());
        when(actorBOMapper.listEntitytoListBo(List.of())).thenReturn(actors);

        // Ejecución del método
        List<ActorBO> result = actorService.getAllActors();

        // Verificaciones

        assertEquals(5, result.size());

        assertEquals("John Doe", result.get(0).getName());
        assertEquals(30, result.get(0).getAge());
        assertEquals("ESP", result.get(0).getNation());

        assertEquals("John Doe 1", result.get(1).getName());
        assertEquals(30, result.get(1).getAge());
        assertEquals("ING", result.get(1).getNation());

        assertEquals("John Doe 2", result.get(2).getName());
        assertEquals(30, result.get(2).getAge());
        assertEquals("ESP", result.get(2).getNation());

        assertEquals("John Doe 3", result.get(3).getName());
        assertEquals(30, result.get(3).getAge());
        assertEquals("FRA", result.get(3).getNation());

        assertEquals("John Doe 4", result.get(4).getName());
        assertEquals(30, result.get(4).getAge());
        assertEquals("ESP", result.get(4).getNation());

    }

    /**
     * Test get actor by id valid id.
     */
    @Test
    @DisplayName("Test get actor with valid id")
    void testGetActorById_validId() {
        // Configuración de mocks
        when(actorRepository.findById(ID)).thenReturn(Optional.ofNullable(actor));
        when(actorBOMapper.entityToBo(actor)).thenReturn(actorBO);

        // Ejecución del método
        ActorBO result = actorService.getActorById(ID);

        // Verificaciones
        assertEquals("John Doe", result.getName());
        assertEquals(30, result.getAge());
        assertEquals("ESP", result.getNation());

    }

    /**
     * Test get actor by id actor null.
     */
    @Test
    @DisplayName("Test get actor with null actor")
    public void testGetActorById_actorNull() {
        // Configuración de mocks
        when(actorRepository.findById(ID)).thenReturn(Optional.empty());

        // Ejecución del método
        ActorBO result = actorService.getActorById(ID);

        // Verificaciones
        assertNotNull(result);
        assertEquals(ActorBO.builder().build(), result);
    }

    /**
     * Test update actor valid data.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test update actor valid data")
    void testUpdateActor_validData() throws Exception {
        // Configuración de mocks
        when(actorRepository.existsById(ID)).thenReturn(true);
        when(actorBOMapper.boToEntity(actorBO)).thenReturn(actor);

        // Ejecución del método
        boolean result = actorService.updateActor(ID, actorBO);

        // Verificaciones
        assertTrue(result);

    }

    /**
     * Test update actor found.
     */
    @Test
    @DisplayName("Test update actor with actor null")
    void testUpdateActor_actorFound() {
        // Configuración de mocks
        when(actorRepository.existsById(ID)).thenReturn(true);
        when(actorBOMapper.boToEntity(actorBO)).thenReturn(new Actor());
        when(actorRepository.save(any(Actor.class))).thenReturn(new Actor());

        // Ejecución del método
        boolean result = actorService.updateActor(ID, actorBO);

        // Verificaciones
        assertTrue(result);
    }

    /**
     * Test update actor actor not found.
     */
    @Test
    @DisplayName("Test update actor with actor notFound")
    void testUpdateActor_actorNotFound() {

        // Configuración de mocks
        when(actorRepository.existsById(ID)).thenReturn(false);

        // Ejecución del método
        try {
            actorService.updateActor(ID, actorBO);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Actor not found", e.getMessage());
        }
    }

    /**
     * Test update actor update failed.
     */
    @Test
    @DisplayName("Test update actor with actor failed")
    void testUpdateActor_updateFailed() {
        // Configuración de mocks
        when(actorRepository.existsById(ID)).thenReturn(true);
        when(actorBOMapper.boToEntity(actorBO)).thenReturn(new Actor());
        when(actorRepository.save(any(Actor.class))).thenThrow(new RuntimeException("Error updating actor"));

        // Ejecución del método
        try {
            actorService.updateActor(ID, actorBO);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Failed to update actor: ", e.getMessage());
        }
    }

    @Test
    @DisplayName("Tets delete actor with actor not found")
    void testDeleteActor_actorNotFound() {
        // Configuración de mocks
        when(actorRepository.existsById(ID)).thenReturn(false);

        // Ejecución del método
        try {
            actorService.deleteActor(ID);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Actor not found", e.getMessage());
        }
    }

}

