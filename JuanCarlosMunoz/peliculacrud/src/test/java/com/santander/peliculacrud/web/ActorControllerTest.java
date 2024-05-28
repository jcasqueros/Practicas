package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.dto.ActorDTO;
import com.santander.peliculacrud.service.ActorServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.mapper.dto.ActorDTOMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * The type Actor controller test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ActorControllerTest {

    @Mock
    private ActorServiceInterface actorService;

    @Mock
    private ActorDTOMapper actorDTOMapper;

    @Mock
    private CommonOperation commonOperation;

    @InjectMocks
    private ActorController actorController;

    /**
     * Test create actor.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test create Actor with valid data")
    void testCreateActor() throws Exception {
        // Arrange
        ActorDTO actorDTO = ActorDTO.builder().name("John Doe").age(30).name("ESP").build();
        ActorBO actorBO = ActorBO.builder().name("John Doe").age(30).name("ESP").build();

        when(actorDTOMapper.dtoToBo(actorDTO)).thenReturn(actorBO);
        when(actorService.createActor(actorBO)).thenReturn(actorBO);

        // Act
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(actorDTO, "actorDTO");
        ResponseEntity<String> response = actorController.createActor(actorDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Actor created successfully", response.getBody());
    }

    /**
     * Test create actor invalid request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test create actor with invalid request")
    void testCreateActor_InvalidRequest() throws Exception {
        // Arrange
        ActorDTO actorDTO = ActorDTO.builder().name(null).age(-1).nation(null).build();
        BindingResult bindingResult = new BeanPropertyBindingResult(actorDTO, "actorDTO");
        bindingResult.rejectValue("name", "error.name.null");
        bindingResult.rejectValue("age", "error.age.invalid");
        bindingResult.rejectValue("nation", "error.nation.null");

        Logger logger = LoggerFactory.getLogger(ActorController.class);

        // Stubbing commonOperation
        doNothing().when(commonOperation).showErrorModel(logger, bindingResult);

        // Act
        ResponseEntity<String> response = actorController.createActor(actorDTO, bindingResult);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Actor not created", response.getBody());
    }

    /**
     * Test update actor.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test update actor with valid data")
    void testUpdateActor() throws Exception {
        // Arrange
        long id = 1L;
        ActorDTO updatedActorDTO = ActorDTO.builder().name("John Doe").age(30).name("ESP").build();
        ActorBO updatedActorBO = ActorBO.builder().name("John Doe").age(30).name("ESP").build();

        when(actorDTOMapper.dtoToBo(updatedActorDTO)).thenReturn(updatedActorBO);
        when(actorService.updateActor(id, updatedActorBO)).thenReturn(true);

        // Act
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(updatedActorDTO, "actorDTO");

        ResponseEntity<String> response = actorController.updateActor(id, updatedActorDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Actor updated successfully", response.getBody());
    }

    /**
     * Test update actor invalid request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test update actor with invalid request")
    void testUpdateActor_InvalidRequest() throws Exception {

        // Arrange
        ActorDTO actorDTO = ActorDTO.builder().name(null).age(-1).nation(null).build();
        BindingResult bindingResult = new BeanPropertyBindingResult(actorDTO, "actorDTO");
        bindingResult.rejectValue("name", "error.name.null");
        bindingResult.rejectValue("age", "error.age.invalid");
        bindingResult.rejectValue("nation", "error.nation.null");

        Logger logger = LoggerFactory.getLogger(ActorController.class);

        // Stubbing commonOperation
        doNothing().when(commonOperation).showErrorModel(logger, bindingResult);

        // Act
        ResponseEntity<String> response = actorController.updateActor(1L, actorDTO, bindingResult);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Actor not update", response.getBody());

    }

    /**
     * Test delete actor.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test delete actor with valid id")
    void testDeleteActor() throws Exception {
        // Arrange
        long id = 1L;
        when(actorService.deleteActor(id)).thenReturn(true);

        // Act
        ResponseEntity<String> response = actorController.deleteActor(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Actor delete", response.getBody());
    }

    /**
     * Test get all actors.
     */
    @Test
    @DisplayName("Test get all actor with size 2")
    void testGetAllActors() {
        // Arrange
        List<ActorBO> actorBOS = Arrays.asList(ActorBO.builder().build(), ActorBO.builder().build());
        List<ActorDTO> actorDTOS = Arrays.asList(ActorDTO.builder().build(), ActorDTO.builder().build());
        when(actorService.getAllActors(0)).thenReturn(actorBOS);
        when(actorDTOMapper.bosToDtos(actorBOS)).thenReturn(actorDTOS);
        // Act
        ResponseEntity<List<ActorDTO>> response = actorController.getAllActors(0);

        // Assert
        assertNotNull(response);
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get actor by id.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test get actor with valid id")
    void testGetActorById() throws Exception {
        // Arrange
        long id = 1L;
        ActorBO actorBO = ActorBO.builder().name("John Doe").age(30).name("ESP").build();
        ActorDTO actorDTO = ActorDTO.builder().name("John Doe").age(30).nation("ESP").build();

        when(actorService.getActorById(id)).thenReturn(actorBO);
        when(actorDTOMapper.boToDto(actorBO)).thenReturn(actorDTO);

        // Act
        ResponseEntity<ActorDTO> response = actorController.getActorById(id);

        // Assert
        assertNotNull(response);
        ActorDTO actorReturn = response.getBody();
        assert actorReturn != null;
        assertEquals("John Doe", actorReturn.getName());
        assertEquals(30, actorReturn.getAge());
    }

    /**
     * Test get actor by id null actor.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test get actor with invalid id")
    void testGetActorByIdNullActor() throws Exception {
        // Arrange
        long id = 1L;

        when(actorService.getActorById(id)).thenReturn(null);

        // Act
        ResponseEntity<ActorDTO> response = actorController.getActorById(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test get actor by id null.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test get actor with invalid id")
    void testGetActorByIdNull() throws Exception {
        // Arrange
        long id = 1L;
        ActorBO actorBO = ActorBO.builder().name("John Doe").age(30).name("ESP").build();

        when(actorService.getActorById(id)).thenReturn(actorBO);
        when(actorDTOMapper.boToDto(actorBO)).thenReturn(null);

        // Act
        ResponseEntity<ActorDTO> response = actorController.getActorById(id);

        // Assert
        assertEquals("200 OK", response.getStatusCode().toString());
    }

    /**
     * Test get actors by nation.
     */
    @Test
    @DisplayName("Test get actors by nation with valid nation")
    void testGetActorsByNation() {
        // Arrange
        String nation = "ESP";
        int page = 0;
        List<ActorBO> actorBOs = Arrays.asList(ActorBO.builder().name("PRV11 Doe").age(30).nation("ESP").build(),
                ActorBO.builder().name("PRV12 Doe").age(35).nation("ESP").build());
        List<ActorDTO> actorDTOS = Arrays.asList(ActorDTO.builder().name("PRV13 Doe").age(30).nation("ESP").build(),
                ActorDTO.builder().name("PRV14 Doe").age(35).nation("ESP").build());

        when(actorService.getActorByNation(nation, page)).thenReturn(actorBOs);
        when(actorDTOMapper.bosToDtos(actorBOs)).thenReturn(actorDTOS);

        // Act
        ResponseEntity<List<ActorDTO>> response = actorController.getActorsByNation(nation, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get actors by nation not found.
     */
    @Test
    @DisplayName("Test get actors by nation with invalid nation")
    void testGetActorsByNationNotFound() {
        // Arrange
        String nation = "Invalid Nation";
        int page = 0;

        when(actorService.getActorByNation(nation, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<ActorDTO>> response = actorController.getActorsByNation(nation, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test get actors by name.
     */
    @Test
    @DisplayName("Test get actors by name with valid name")
    void testGetActorsByName() {
        // Arrange
        String name = "PRV15 Doe";
        int page = 0;
        List<ActorBO> actorBOs = Arrays.asList(ActorBO.builder().name("PRV16 Doe").age(30).nation("ESP").build(),
                ActorBO.builder().name("PRV17 Doe").age(35).nation("ESP").build());
        List<ActorDTO> actorDTOS = Arrays.asList(ActorDTO.builder().name("PRV18 Doe").age(30).nation("ESP").build(),
                ActorDTO.builder().name("PRV19 Doe").age(35).nation("ESP").build());

        when(actorService.getActorByName(name, page)).thenReturn(actorBOs);
        when(actorDTOMapper.bosToDtos(actorBOs)).thenReturn(actorDTOS);

        // Act
        ResponseEntity<List<ActorDTO>> response = actorController.getActorsByName(name, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get actors by name not found.
     */
    @Test
    @DisplayName("Test get actors by name with invalid name")
    void testGetActorsByNameNotFound() {
        // Arrange
        String name = "Invalid Name";
        int page = 0;

        when(actorService.getActorByName(name, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<ActorDTO>> response = actorController.getActorsByName(name, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test get actors by age.
     */
    @Test
    @DisplayName("Test get actors by age with valid age")
    void testGetActorsByAge() {
        // Arrange
        int age = 30;
        int page = 0;
        List<ActorBO> actorBOs = Arrays.asList(ActorBO.builder().name("PRV20 Doe").age(30).nation("ESP").build(),
                ActorBO.builder().name("PRV21 Doe").age(30).nation("ESP").build());
        List<ActorDTO> actorDTOS = Arrays.asList(ActorDTO.builder().name("PRV22 Doe").age(30).nation("ESP").build(),
                ActorDTO.builder().name("PRV23 Doe").age(30).nation("ESP").build());

        when(actorService.getActorByAge(age, page)).thenReturn(actorBOs);
        when(actorDTOMapper.bosToDtos(actorBOs)).thenReturn(actorDTOS);

        // Act
        ResponseEntity<List<ActorDTO>> response = actorController.getActorsByAge(age, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get actors by age not found.
     */
    @Test
    @DisplayName("Test get actors by age with invalid age")
    void testGetActorsByAgeNotFound() {
        // Arrange
        int age = -1;
        int page = 0;

        when(actorService.getActorByAge(age, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<ActorDTO>> response = actorController.getActorsByAge(age, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
