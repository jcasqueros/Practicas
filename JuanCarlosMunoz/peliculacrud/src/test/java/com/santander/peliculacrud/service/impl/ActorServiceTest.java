package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.ActorRepository;
import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.dto.UserDTO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.util.exception.GenericException;
import com.santander.peliculacrud.util.mapper.bo.ActorBOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;

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
    private EndpointService endpointService;

    private ActorBO actorBO;

    private List<ActorBO> ActorBOs;

    private Actor actor;

    private static final long ID = 1;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        ActorBOs = new ArrayList<>();

        actorBO = ActorBO.builder().name("John Doe").age(30).nation("ESP").build();
        actor = Actor.builder().name("John Doe").age(30).nation("ESP").build();

        ActorBOs.add(ActorBO.builder().name("John Doe 1").age(30).nation("ING").build());
        ActorBOs.add(ActorBO.builder().name("John Doe 2").age(30).nation("ESP").build());
        ActorBOs.add(ActorBO.builder().name("John Doe 3").age(30).nation("FRA").build());
        ActorBOs.add(ActorBO.builder().name("John Doe 4").age(30).nation("ESP").build());

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
        when(endpointService.getUserByNameAndAge(actorBO.getName(), actorBO.getAge())).thenReturn(
                List.of(new UserDTO()));
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
     * Test create actor user not found.
     */
    @Test
    @DisplayName("Test create actor user not found")
    void testCreateActor_userNotFound() {
        // Configuración de mocks
        when(actorBOMapper.boToEntity(actorBO)).thenReturn(new Actor());
        when(endpointService.getUserByNameAndAge(actorBO.getName(), actorBO.getAge())).thenReturn(
                Collections.emptyList());

        // Ejecución del método y verificación de la excepción
        GenericException exception = assertThrows(GenericException.class, () -> actorService.createActor(actorBO));
        assertEquals("Failed to create actor: ", exception.getMessage());
    }

    /**
     * Test create actor repository exception.
     */
    @Test
    @DisplayName("Test create actor repository exception")
    void testCreateActor_repositoryException() {
        // Configuración de mocks
        when(actorBOMapper.boToEntity(actorBO)).thenReturn(new Actor());
        when(endpointService.getUserByNameAndAge(actorBO.getName(), actorBO.getAge())).thenReturn(
                List.of(new UserDTO()));
        when(actorRepository.save(any(Actor.class))).thenThrow(new RuntimeException("Failed to create actor: "));

        // Ejecución del método y verificación de la excepción
        GenericException exception = assertThrows(GenericException.class, () -> actorService.createActor(actorBO));
        assertEquals("Failed to create actor: ", exception.getMessage());
    }

    /**
     * Test get all actors with data.
     */
    @Test
    @DisplayName("Test get all actors with data")
    void testGetAllActors_withData() {
        // Configuración de mocks
        Page<Actor> actorsPage = new PageImpl<>(List.of());
        when(actorRepository.findAll(any(Pageable.class))).thenReturn(actorsPage);
        when(actorBOMapper.listEntitytoListBo(actorsPage)).thenReturn(ActorBOs);

        // Ejecución del método
        List<ActorBO> result = actorService.getAllActors(0);

        // Verificaciones
        assertEquals(4, result.size());

        assertEquals("John Doe 1", result.get(0).getName());
        assertEquals(30, result.get(0).getAge());
        assertEquals("ING", result.get(0).getNation());

        assertEquals("John Doe 2", result.get(1).getName());
        assertEquals(30, result.get(1).getAge());
        assertEquals("ESP", result.get(1).getNation());

        assertEquals("John Doe 3", result.get(2).getName());
        assertEquals(30, result.get(2).getAge());
        assertEquals("FRA", result.get(2).getNation());

        assertEquals("John Doe 4", result.get(3).getName());
        assertEquals(30, result.get(3).getAge());
        assertEquals("ESP", result.get(3).getNation());
    }

    /**
     * Test get all actors with no data.
     */
    @Test
    @DisplayName("Test get all actors with no data")
    void testGetAllActors_withNoData() {
        // Configuración de mocks
        Page<Actor> actorsPage = new PageImpl<>(Collections.emptyList());
        when(actorRepository.findAll(any(Pageable.class))).thenReturn(actorsPage);
        when(actorBOMapper.listEntitytoListBo(actorsPage)).thenReturn(Collections.emptyList());

        // Ejecución del método
        List<ActorBO> result = actorService.getAllActors(0);

        // Verificaciones
        assertEquals(0, result.size());
    }

    /**
     * Test get all actors repository exception.
     */
    @Test
    @DisplayName("Test get all actors with repository exception")
    void testGetAllActors_repositoryException() {
        // Configuración de mocks
        when(actorRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException("Failed to get all actors"));

        // Ejecución del método y verificación de la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> actorService.getAllActors(0));
        assertEquals("Failed to get all actors", exception.getMessage());
    }

    /**
     * Test get actor by id valid id.
     *
     * @throws GenericException
     *         the generic exception
     */
    @Test
    @DisplayName("Test get actor with valid id")
    void testGetActorById_validId() throws GenericException {
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
    void testGetActorById_actorNull() {
        // Configuración de mocks
        when(actorRepository.findById(ID)).thenReturn(Optional.empty());

        GenericException exception = assertThrows(GenericException.class, () -> actorService.getActorById(ID));
        assertEquals("Actor not found", exception.getMessage());
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
     *
     * @throws GenericException
     *         the generic exception
     */
    @Test
    @DisplayName("Test update actor with actor null")
    void testUpdateActor_actorFound() throws GenericException {
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
     * Test update actor not found.
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
        } catch (RuntimeException | GenericException e) {
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
        } catch (RuntimeException | GenericException e) {
            assertEquals("Failed to update actor: ", e.getMessage());
        }
    }

    /**
     * Test delete actor actor not found.
     */
    @Test
    @DisplayName("Tets delete actor with actor not found")
    void testDeleteActor_actorNotFound() {
        // Configuración de mocks
        when(actorRepository.existsById(ID)).thenReturn(false);

        // Ejecución del método
        try {
            actorService.deleteActor(ID);
            fail("Expected RuntimeException");
        } catch (RuntimeException | GenericException e) {
            assertEquals("Actor not found", e.getMessage());
        }
    }

    /**
     * Test delete actor actor found.
     */
    @Test
    @DisplayName("Tets delete actor with actor found")
    void testDeleteActor_actorFound() {
        // Configuración de mocks
        when(actorRepository.existsById(ID)).thenReturn(true);
        doNothing().when(actorRepository).deleteById(1L);

        // Ejecución del método
        try {
            boolean deleted = actorService.deleteActor(ID);
            assertTrue(deleted);
        } catch (RuntimeException | GenericException e) {
            fail("RuntimeException");
        }
    }

    /**
     * Test get actor by nation with no data.
     */
    @Test
    @DisplayName("Test getActorByNation with no data")
    void testGetActorByNation_withNoData() {
        // Configuración de mocks
        String nation = "ESP";
        int page = 0;
        Page<Actor> actorsPage = new PageImpl<>(Collections.emptyList());
        when(actorRepository.findAllByNationEquals(eq(nation), any(Pageable.class))).thenReturn(actorsPage);
        when(actorBOMapper.listEntitytoListBo(any(Page.class))).thenReturn(Collections.emptyList());

        // Ejecución del método
        List<ActorBO> result = actorService.getActorByNation(nation, page);

        // Verificaciones
        assertEquals(0, result.size());
    }

    /**
     * Test get actor by age with no data.
     */
    @Test
    @DisplayName("Test getActorByAge with no data")
    void testGetActorByAge_withNoData() {
        // Configuración de mocks
        int age = 30;
        int page = 0;
        Page<Actor> actorsPage = new PageImpl<>(Collections.emptyList());
        when(actorRepository.findAllByAgeEquals(eq(age), any(Pageable.class))).thenReturn(actorsPage);
        when(actorBOMapper.listEntitytoListBo(actorsPage)).thenReturn(Collections.emptyList());

        // Ejecución del método
        List<ActorBO> result = actorService.getActorByAge(age, page);

        // Verificaciones
        assertEquals(0, result.size());
    }

    /**
     * Test get actor by age with multiple pages.
     */
    @Test
    @DisplayName("Test getActorByAge with multiple pages")
    void testGetActorByAge_withMultiplePages() {
        // Configuración de mocks
        int age = 30;
        List<Actor> actorsListPage1 = Arrays.asList(Actor.builder().name("PRV Doe 4").age(age).nation("ESP").build(),
                Actor.builder().name("PRV Doe 1").age(age).nation("ING").build(),
                Actor.builder().name("PRV Doe 2").age(age).nation("ESP").build(),
                Actor.builder().name("PRV Doe 3").age(age).nation("FRA").build(),
                Actor.builder().name("PRV Doe").age(age).nation("ESP").build());
        List<Actor> actorsListPage2 = Arrays.asList(Actor.builder().name("PRV Doe 5").age(age).nation("ESP").build(),
                Actor.builder().name("PRV Doe 6").age(age).nation("ING").build());
        Page<Actor> actorsPage1 = new PageImpl<>(actorsListPage1);
        Page<Actor> actorsPage2 = new PageImpl<>(actorsListPage2);
        when(actorRepository.findAllByAgeEquals(age, PageRequest.of(0, 5))).thenReturn(actorsPage1);
        when(actorRepository.findAllByAgeEquals(age, PageRequest.of(1, 5))).thenReturn(actorsPage2);
        when(actorBOMapper.listEntitytoListBo(actorsPage1)).thenReturn(actorsListPage1.stream()
                .map(actor -> ActorBO.builder().name(actor.getName()).age(actor.getAge()).nation(actor.getNation())
                        .build()).collect(Collectors.toList()));
        when(actorBOMapper.listEntitytoListBo(actorsPage2)).thenReturn(actorsListPage2.stream()
                .map(actor -> ActorBO.builder().name(actor.getName()).age(actor.getAge()).nation(actor.getNation())
                        .build()).collect(Collectors.toList()));

        // Ejecución del método
        List<ActorBO> resultPage1 = actorService.getActorByAge(age, 0);
        List<ActorBO> resultPage2 = actorService.getActorByAge(age, 1);

        // Verificaciones
        assertEquals(5, resultPage1.size());
        assertEquals(2, resultPage2.size());
    }

    /**
     * Test get actor by age with data.
     */
    @Test
    @DisplayName("Test getActorByAge with data")
    void testGetActorByAge_withData() {
        // Configuración de mocks
        int age = 30;
        int page = 0;
        List<Actor> actorsList = Arrays.asList(Actor.builder().name("PRV Doe 4").age(age).nation("ESP").build(),
                Actor.builder().name("PRV Doe 1").age(age).nation("ING").build(),
                Actor.builder().name("PRV Doe 2").age(age).nation("ESP").build(),
                Actor.builder().name("PRV Doe 3").age(age).nation("FRA").build(),
                Actor.builder().name("PRV Doe").age(age).nation("ESP").build());
        Page<Actor> actorsPage = new PageImpl<>(actorsList);
        when(actorRepository.findAllByAgeEquals(eq(age), any(Pageable.class))).thenReturn(actorsPage);
        when(actorBOMapper.listEntitytoListBo(actorsPage)).thenReturn(actorsList.stream()
                .map(actor -> ActorBO.builder().name(actor.getName()).age(actor.getAge()).nation(actor.getNation())
                        .build()).collect(Collectors.toList()));

        // Ejecución del método
        List<ActorBO> result = actorService.getActorByAge(age, page);

        // Verificaciones
        assertEquals(5, result.size());

        assertEquals("PRV Doe", result.get(0).getName());
        assertEquals(age, result.get(0).getAge());
        assertEquals("ESP", result.get(0).getNation());

        assertEquals("PRV Doe 1", result.get(1).getName());
        assertEquals(age, result.get(1).getAge());
        assertEquals("ING", result.get(1).getNation());

        assertEquals("PRV Doe 2", result.get(2).getName());
        assertEquals(age, result.get(2).getAge());
        assertEquals("ESP", result.get(2).getNation());

        assertEquals("PRV Doe 3", result.get(3).getName());
        assertEquals(age, result.get(3).getAge());
        assertEquals("FRA", result.get(3).getNation());

        assertEquals("PRV Doe 4", result.get(4).getName());
        assertEquals(age, result.get(4).getAge());
        assertEquals("ESP", result.get(4).getNation());
    }

    /**
     * Test get actor by name with multiple pages.
     */
    @Test
    @DisplayName("Test getActorByName with multiple pages")
    void testGetActorByName_withMultiplePages() {
        // Configuración de mocks
        String name = "PRV Doe";
        List<Actor> actorsListPage1 = Arrays.asList(Actor.builder().name(name).age(30).nation("ESP").build(),
                Actor.builder().name(name).age(31).nation("ING").build(),
                Actor.builder().name(name).age(32).nation("ESP").build(),
                Actor.builder().name(name).age(33).nation("FRA").build(),
                Actor.builder().name(name).age(34).nation("ESP").build());
        List<Actor> actorsListPage2 = Arrays.asList(Actor.builder().name(name).age(35).nation("ESP").build(),
                Actor.builder().name(name).age(36).nation("ING").build());
        Page<Actor> actorsPage1 = new PageImpl<>(actorsListPage1);
        Page<Actor> actorsPage2 = new PageImpl<>(actorsListPage2);
        when(actorRepository.findAllByName(name, PageRequest.of(0, 5))).thenReturn(actorsPage1);
        when(actorRepository.findAllByName(name, PageRequest.of(1, 5))).thenReturn(actorsPage2);
        when(actorBOMapper.listEntitytoListBo(actorsPage1)).thenReturn(actorsListPage1.stream()
                .map(actor -> ActorBO.builder().name(actor.getName()).age(actor.getAge()).nation(actor.getNation())
                        .build()).collect(Collectors.toList()));
        when(actorBOMapper.listEntitytoListBo(actorsPage2)).thenReturn(actorsListPage2.stream()
                .map(actor -> ActorBO.builder().name(actor.getName()).age(actor.getAge()).nation(actor.getNation())
                        .build()).collect(Collectors.toList()));

        // Ejecución del método
        List<ActorBO> resultPage1 = actorService.getActorByName(name, 0);
        List<ActorBO> resultPage2 = actorService.getActorByName(name, 1);

        // Verificaciones
        assertEquals(5, resultPage1.size());
        assertEquals(2, resultPage2.size());
    }

}

