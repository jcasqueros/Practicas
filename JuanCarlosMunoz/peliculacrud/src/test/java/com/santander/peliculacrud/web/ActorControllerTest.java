package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.dto.ActorDTO;
import com.santander.peliculacrud.service.ActorServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.mapper.ActorDTOMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    private CommonOperation commonOperation;

    @Mock
    private ActorDTOMapper actorDTOMapper;

    @InjectMocks
    private ActorController actorController;

    @Test
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

    @Test
    void testCreateActor_InvalidRequest() throws Exception {
        // Arrange
        ActorDTO actorDTO = ActorDTO.builder().name(null).age(-1).nation(null).build();

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(actorDTO, "actorDTO");


        // Act
        ResponseEntity<String> response = actorController.createActor(actorDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Actor not created", response.getBody());
    }

    @Test
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

    @Test
    void testUpdateActor_InvalidRequest() throws Exception {
        // Arrange
        long id = 1L;
        ActorDTO updatedActorDTO = ActorDTO.builder().name(null).age(-1).name(null).build();

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(updatedActorDTO, "actorDTO");

        // Act
        ResponseEntity<String> response = actorController.updateActor(id, updatedActorDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Actor not update", response.getBody());
    }

    @Test
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

    @Test
    void testGetAllActors() throws Exception {
        // Arrange
        List<ActorBO> actorBOS = Arrays.asList(ActorBO.builder().build(), ActorBO.builder().build());
        List<ActorDTO> actorDTOS = Arrays.asList(ActorDTO.builder().build(), ActorDTO.builder().build());
        when(actorService.getAllActors()).thenReturn(actorBOS);
        when(actorDTOMapper.bosToDtos(actorBOS)).thenReturn(actorDTOS);
        // Act
        List<ActorDTO> response = actorController.getAllActors();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    void testGetActorById() throws Exception {
        // Arrange
        long id = 1L;
        ActorBO actorBO = ActorBO.builder().name("John Doe").age(30).name("ESP").build();
        ActorDTO actorDTO = ActorDTO.builder().name("John Doe").age(30).nation("ESP").build();


        when(actorService.getActorById(id)).thenReturn(actorBO);
        when(actorDTOMapper.boToDto(actorBO)).thenReturn(actorDTO);

        // Act
        ActorDTO response = actorController.getActorById(id);

        // Assert
        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        assertEquals(30, response.getAge());
    }
    @Test
    void testGetActorByIdNull() throws Exception {
        // Arrange
        long id = 1L;
        ActorBO actorBO = ActorBO.builder().name("John Doe").age(30).name("ESP").build();
        ActorDTO actorDTO = ActorDTO.builder().name("John Doe").age(30).nation("ESP").build();


        when(actorService.getActorById(id)).thenReturn(actorBO);
        when(actorDTOMapper.boToDto(actorBO)).thenReturn(null);

        // Act
        ActorDTO response = actorController.getActorById(id);

        // Assert
        assertNull(response);
    }

}
