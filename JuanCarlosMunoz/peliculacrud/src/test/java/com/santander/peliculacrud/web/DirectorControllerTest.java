package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.dto.DirectorDTO;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.mapper.DirectorDTOMapper;
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
 * The type Director controller test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DirectorControllerTest {

    @Mock
    private DirectorServiceInterface directorService;

    @Mock
    private CommonOperation commonOperation;

    @Mock
    private DirectorDTOMapper directorDTOMapper;

    @InjectMocks
    private DirectorController directorController;

    @Test
    void testCreateDirector() throws Exception {
        // Arrange
        DirectorDTO directorDTO = DirectorDTO.builder().name("John Doe").age(30).name("ESP").build();
        DirectorBO directorBO = DirectorBO.builder().name("John Doe").age(30).name("ESP").build();

        when(directorDTOMapper.dtoToBo(directorDTO)).thenReturn(directorBO);
        when(directorService.createDirector(directorBO)).thenReturn(directorBO);

        // Act
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(directorDTO, "directorDTO");
        ResponseEntity<String> response = directorController.createDirector(directorDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Director created successfully", response.getBody());
    }

    @Test
    void testCreateDirector_InvalidRequest() throws Exception {
        // Arrange
        DirectorDTO directorDTO = DirectorDTO.builder().name(null).age(-1).nation(null).build();

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(directorDTO, "directorDTO");


        // Act
        ResponseEntity<String> response = directorController.createDirector(directorDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Director not created", response.getBody());
    }

    @Test
    void testUpdateDirector() throws Exception {
        // Arrange
        long id = 1L;
        DirectorDTO updatedDirectorDTO = DirectorDTO.builder().name("John Doe").age(30).name("ESP").build();
        DirectorBO updatedDirectorBO = DirectorBO.builder().name("John Doe").age(30).name("ESP").build();

        when(directorDTOMapper.dtoToBo(updatedDirectorDTO)).thenReturn(updatedDirectorBO);
        when(directorService.updateDirector(id, updatedDirectorBO)).thenReturn(true);

        // Act
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(updatedDirectorDTO, "directorDTO");

        ResponseEntity<String> response = directorController.updateDirector(id, updatedDirectorDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Director updated successfully", response.getBody());
    }

    @Test
    void testUpdateDirector_InvalidRequest() throws Exception {
        // Arrange
        long id = 1L;
        DirectorDTO updatedDirectorDTO = DirectorDTO.builder().name(null).age(-1).name(null).build();

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(updatedDirectorDTO, "directorDTO");

        // Act
        ResponseEntity<String> response = directorController.updateDirector(id, updatedDirectorDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Director not update", response.getBody());
    }

    @Test
    void testDeleteDirector() throws Exception {
        // Arrange
        long id = 1L;
        when(directorService.deleteDirector(id)).thenReturn(true);

        // Act
        ResponseEntity<String> response = directorController.deleteDirector(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Director delete", response.getBody());
    }

    @Test
    void testGetAllDirectors() throws Exception {
        // Arrange
        List<DirectorBO> directorBOS = Arrays.asList(DirectorBO.builder().build(), DirectorBO.builder().build());
        List<DirectorDTO> directorDTOS = Arrays.asList(DirectorDTO.builder().build(), DirectorDTO.builder().build());
        when(directorService.getAllDirectors()).thenReturn(directorBOS);
        when(directorDTOMapper.bosToDtos(directorBOS)).thenReturn(directorDTOS);
        // Act
        List<DirectorDTO> response = directorController.getAllDirectors();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    void testGetDirectorById() throws Exception {
        // Arrange
        long id = 1L;
        DirectorBO directorBO = DirectorBO.builder().name("John Doe").age(30).name("ESP").build();
        DirectorDTO directorDTO = DirectorDTO.builder().name("John Doe").age(30).nation("ESP").build();


        when(directorService.getDirectorById(id)).thenReturn(directorBO);
        when(directorDTOMapper.boToDto(directorBO)).thenReturn(directorDTO);

        // Act
        DirectorDTO response = directorController.getDirectorById(id);

        // Assert
        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        assertEquals(30, response.getAge());
    }
    @Test
    void testGetDirectorByIdNull() throws Exception {
        // Arrange
        long id = 1L;
        DirectorBO directorBO = DirectorBO.builder().name("John Doe").age(30).name("ESP").build();
        DirectorDTO directorDTO = DirectorDTO.builder().name("John Doe").age(30).nation("ESP").build();


        when(directorService.getDirectorById(id)).thenReturn(directorBO);
        when(directorDTOMapper.boToDto(directorBO)).thenReturn(null);

        // Act
        DirectorDTO response = directorController.getDirectorById(id);

        // Assert
        assertNull(response);
    }

}
