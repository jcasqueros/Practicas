package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.dto.DirectorDTO;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.mapper.dto.DirectorDTOMapper;
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
 * The type Director controller test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DirectorControllerTest {

    @Mock
    private DirectorServiceInterface directorService;

    @Mock
    private DirectorDTOMapper directorDTOMapper;

    @Mock
    private CommonOperation commonOperation;

    @InjectMocks
    private DirectorController directorController;

    /**
     * Test create director.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test create Director with valid data")
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

    /**
     * Test create director invalid request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test create director with invalid request")
    void testCreateDirector_InvalidRequest() throws Exception {
        // Arrange
        DirectorDTO directorDTO = DirectorDTO.builder().name(null).age(-1).nation(null).build();
        BindingResult bindingResult = new BeanPropertyBindingResult(directorDTO, "directorDTO");
        bindingResult.rejectValue("name", "error.name.null");
        bindingResult.rejectValue("age", "error.age.invalid");
        bindingResult.rejectValue("nation", "error.nation.null");

        Logger logger = LoggerFactory.getLogger(DirectorController.class);

        // Stubbing commonOperation
        doNothing().when(commonOperation).showErrorModel(logger, bindingResult);

        // Act
        ResponseEntity<String> response = directorController.createDirector(directorDTO, bindingResult);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Director not created", response.getBody());
    }

    /**
     * Test update director.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test update director with valid data")
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

    /**
     * Test update director invalid request.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test update director with invalid request")
    void testUpdateDirector_InvalidRequest() throws Exception {

        // Arrange
        DirectorDTO directorDTO = DirectorDTO.builder().name(null).age(-1).nation(null).build();
        BindingResult bindingResult = new BeanPropertyBindingResult(directorDTO, "directorDTO");
        bindingResult.rejectValue("name", "error.name.null");
        bindingResult.rejectValue("age", "error.age.invalid");
        bindingResult.rejectValue("nation", "error.nation.null");

        Logger logger = LoggerFactory.getLogger(DirectorController.class);

        // Stubbing commonOperation
        doNothing().when(commonOperation).showErrorModel(logger, bindingResult);

        // Act
        ResponseEntity<String> response = directorController.updateDirector(1L, directorDTO, bindingResult);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Director not update", response.getBody());

    }

    /**
     * Test delete director.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test delete director with valid id")
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

    /**
     * Test get all directors.
     */
    @Test
    @DisplayName("Test get all director with size 2")
    void testGetAllDirectors() {
        // Arrange
        List<DirectorBO> directorBOS = Arrays.asList(DirectorBO.builder().build(), DirectorBO.builder().build());
        List<DirectorDTO> directorDTOS = Arrays.asList(DirectorDTO.builder().build(), DirectorDTO.builder().build());
        when(directorService.getAllDirectors(0)).thenReturn(directorBOS);
        when(directorDTOMapper.bosToDtos(directorBOS)).thenReturn(directorDTOS);
        // Act
        ResponseEntity<List<DirectorDTO>> response = directorController.getAllDirectors(0);

        // Assert
        assertNotNull(response);
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get director by id.
     */
    @Test
    @DisplayName("Test get director with valid id")
    void testGetDirectorById() {
        // Arrange
        long id = 1L;
        DirectorBO directorBO = DirectorBO.builder().name("John Doe").age(30).name("ESP").build();
        DirectorDTO directorDTO = DirectorDTO.builder().name("John Doe").age(30).nation("ESP").build();

        when(directorService.getDirectorById(id)).thenReturn(directorBO);
        when(directorDTOMapper.boToDto(directorBO)).thenReturn(directorDTO);

        // Act
        ResponseEntity<DirectorDTO> response = directorController.getDirectorById(id);

        // Assert
        assertNotNull(response);
        DirectorDTO directorReturn = response.getBody();
        assert directorReturn != null;
        assertEquals("John Doe", directorReturn.getName());
        assertEquals(30, directorReturn.getAge());
    }

    /**
     * Test get director by id null director.
     */
    @Test
    @DisplayName("Test get director with invalid id")
    void testGetDirectorByIdNullDirector() {
        // Arrange
        long id = 1L;

        when(directorService.getDirectorById(id)).thenReturn(null);

        // Act
        ResponseEntity<DirectorDTO> response = directorController.getDirectorById(id);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test get director by id null.
     */
    @Test
    @DisplayName("Test get director with invalid id")
    void testGetDirectorByIdNull() {
        // Arrange
        long id = 1L;
        DirectorBO directorBO = DirectorBO.builder().name("John Doe").age(30).name("ESP").build();

        when(directorService.getDirectorById(id)).thenReturn(directorBO);
        when(directorDTOMapper.boToDto(directorBO)).thenReturn(null);

        // Act
        ResponseEntity<DirectorDTO> response = directorController.getDirectorById(id);

        // Assert
        assertEquals("200 OK", response.getStatusCode().toString());
    }

    /**
     * Test get directors by nation.
     */
    @Test
    @DisplayName("Test get directors by nation with valid nation")
    void testGetDirectorsByNation() {
        // Arrange
        String nation = "ESP";
        int page = 0;
        List<DirectorBO> directorBOs = Arrays.asList(
                DirectorBO.builder().name("PRV11 Doe").age(30).nation("ESP").build(),
                DirectorBO.builder().name("PRV12 Doe").age(35).nation("ESP").build());
        List<DirectorDTO> directorDTOS = Arrays.asList(
                DirectorDTO.builder().name("PRV13 Doe").age(30).nation("ESP").build(),
                DirectorDTO.builder().name("PRV14 Doe").age(35).nation("ESP").build());

        when(directorService.getDirectorByNation(nation, page)).thenReturn(directorBOs);
        when(directorDTOMapper.bosToDtos(directorBOs)).thenReturn(directorDTOS);

        // Act
        ResponseEntity<List<DirectorDTO>> response = directorController.getDirectorsByNation(nation, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get directors by nation not found.
     */
    @Test
    @DisplayName("Test get directors by nation with invalid nation")
    void testGetDirectorsByNationNotFound() {
        // Arrange
        String nation = "Invalid Nation";
        int page = 0;

        when(directorService.getDirectorByNation(nation, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<DirectorDTO>> response = directorController.getDirectorsByNation(nation, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test get directors by name.
     */
    @Test
    @DisplayName("Test get directors by name with valid name")
    void testGetDirectorsByName() {
        // Arrange
        String name = "PRV15 Doe";
        int page = 0;
        List<DirectorBO> directorBOs = Arrays.asList(
                DirectorBO.builder().name("PRV16 Doe").age(30).nation("ESP").build(),
                DirectorBO.builder().name("PRV17 Doe").age(35).nation("ESP").build());
        List<DirectorDTO> directorDTOS = Arrays.asList(
                DirectorDTO.builder().name("PRV18 Doe").age(30).nation("ESP").build(),
                DirectorDTO.builder().name("PRV19 Doe").age(35).nation("ESP").build());

        when(directorService.getDirectorByName(name, page)).thenReturn(directorBOs);
        when(directorDTOMapper.bosToDtos(directorBOs)).thenReturn(directorDTOS);

        // Act
        ResponseEntity<List<DirectorDTO>> response = directorController.getDirectorsByName(name, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get directors by name not found.
     */
    @Test
    @DisplayName("Test get directors by name with invalid name")
    void testGetDirectorsByNameNotFound() {
        // Arrange
        String name = "Invalid Name";
        int page = 0;

        when(directorService.getDirectorByName(name, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<DirectorDTO>> response = directorController.getDirectorsByName(name, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test get directors by age.
     */
    @Test
    @DisplayName("Test get directors by age with valid age")
    void testGetDirectorsByAge() {
        // Arrange
        int age = 30;
        int page = 0;
        List<DirectorBO> directorBOs = Arrays.asList(
                DirectorBO.builder().name("PRV20 Doe").age(30).nation("ESP").build(),
                DirectorBO.builder().name("PRV21 Doe").age(30).nation("ESP").build());
        List<DirectorDTO> directorDTOS = Arrays.asList(
                DirectorDTO.builder().name("PRV22 Doe").age(30).nation("ESP").build(),
                DirectorDTO.builder().name("PRV23 Doe").age(30).nation("ESP").build());

        when(directorService.getDirectorByAge(age, page)).thenReturn(directorBOs);
        when(directorDTOMapper.bosToDtos(directorBOs)).thenReturn(directorDTOS);

        // Act
        ResponseEntity<List<DirectorDTO>> response = directorController.getDirectorsByAge(age, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    /**
     * Test get directors by age not found.
     */
    @Test
    @DisplayName("Test get directors by age with invalid age")
    void testGetDirectorsByAgeNotFound() {
        // Arrange
        int age = -1;
        int page = 0;

        when(directorService.getDirectorByAge(age, page)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<DirectorDTO>> response = directorController.getDirectorsByAge(age, page);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
