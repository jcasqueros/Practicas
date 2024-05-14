package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.DirectorRepository;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.util.mapper.DirectorBOMapper;
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
 * The type Director service test.
 */
@ExtendWith(MockitoExtension.class)
class DirectorServiceTest {

    @InjectMocks
    private DirectorService directorService;

    @Mock
    private org.springframework.validation.Validator validator;

    @Mock
    private DirectorBOMapper directorBOMapper;

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private Logger logger;

    /**
     * The Result.
     */
    @Mock
    BindingResult result;

    private DirectorBO directorBO;

    private List<DirectorBO> directors;

    private Director director;

    private static final long ID = 1;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        directorBO = DirectorBO.builder().name("John Doe").age(30).nation("ESP").build();
        director = Director.builder().name("John Doe").age(30).nation("ESP").build();

        directors = new ArrayList<>();
        directors.add(DirectorBO.builder().name("John Doe").age(30).nation("ESP").build());
        directors.add(DirectorBO.builder().name("John Doe 1").age(30).nation("ING").build());
        directors.add(DirectorBO.builder().name("John Doe 2").age(30).nation("ESP").build());
        directors.add(DirectorBO.builder().name("John Doe 3").age(30).nation("FRA").build());
        directors.add(DirectorBO.builder().name("John Doe 4").age(30).nation("ESP").build());
    }

    /**
     * Test create director valid data.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test create director valid data")
    void testCreateDirector_validData() throws Exception {
        // Configuración de mocks
        when(directorBOMapper.boToEntity(directorBO)).thenReturn(new Director());
        when(directorRepository.save(any(Director.class))).thenReturn(new Director());
        when(directorBOMapper.entityToBo(any(Director.class))).thenReturn(directorBO);

        // Ejecución del método
        DirectorBO result = directorService.createDirector(directorBO);

        // Verificaciones
        assertNotNull(result);
        assertEquals(directorBO.getName(), result.getName());
        assertEquals(directorBO.getAge(), result.getAge());
        assertEquals(directorBO.getNation(), result.getNation());

    }

    /**
     * Test create director repository exception.
     */
    @Test
    @DisplayName("Test create director repository exception")
    void testCreateDirector_repositoryException() {
        // Configuración de mocks
        when(directorBOMapper.boToEntity(directorBO)).thenReturn(new Director());
        when(directorRepository.save(any(Director.class))).thenThrow(new RuntimeException("Failed to create director: "));

        // Ejecución del método y verificación de la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> directorService.createDirector(directorBO));
        assertEquals("Failed to create director: ", exception.getMessage());
    }

    /**
     * Test get all directors.
     */
    @Test
    @DisplayName("Test get all directors")
    void testGetAllDirectors() {
        // Configuración de mocks
        when(directorRepository.findAll()).thenReturn(List.of());
        when(directorBOMapper.listEntitytoListBo(List.of())).thenReturn(directors);

        // Ejecución del método
        List<DirectorBO> result = directorService.getAllDirectors();

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
     * Test get director by id valid id.
     */
    @Test
    @DisplayName("Test get director with valid id")
    void testGetDirectorById_validId() {
        // Configuración de mocks
        when(directorRepository.findById(ID)).thenReturn(Optional.ofNullable(director));
        when(directorBOMapper.entityToBo(director)).thenReturn(directorBO);

        // Ejecución del método
        DirectorBO result = directorService.getDirectorById(ID);

        // Verificaciones
        assertEquals("John Doe", result.getName());
        assertEquals(30, result.getAge());
        assertEquals("ESP", result.getNation());

    }

    /**
     * Test get director by id director null.
     */
    @Test
    @DisplayName("Test get director with null director")
    public void testGetDirectorById_directorNull() {
        // Configuración de mocks
        when(directorRepository.findById(ID)).thenReturn(Optional.empty());

        // Ejecución del método
        DirectorBO result = directorService.getDirectorById(ID);

        // Verificaciones
        assertNotNull(result);
        assertEquals(DirectorBO.builder().build(), result);
    }

    /**
     * Test update director valid data.
     *
     * @throws Exception
     *         the exception
     */
    @Test
    @DisplayName("Test update director valid data")
    void testUpdateDirector_validData() throws Exception {
        // Configuración de mocks
        when(directorRepository.existsById(ID)).thenReturn(true);
        when(directorBOMapper.boToEntity(directorBO)).thenReturn(director);

        // Ejecución del método
        boolean result = directorService.updateDirector(ID, directorBO);

        // Verificaciones
        assertTrue(result);

    }

    /**
     * Test update director found.
     */
    @Test
    @DisplayName("Test update director with director null")
    void testUpdateDirector_directorFound() {
        // Configuración de mocks
        when(directorRepository.existsById(ID)).thenReturn(true);
        when(directorBOMapper.boToEntity(directorBO)).thenReturn(new Director());
        when(directorRepository.save(any(Director.class))).thenReturn(new Director());

        // Ejecución del método
        boolean result = directorService.updateDirector(ID, directorBO);

        // Verificaciones
        assertTrue(result);
    }

    /**
     * Test update director director not found.
     */
    @Test
    @DisplayName("Test update director with director notFound")
    void testUpdateDirector_directorNotFound() {

        // Configuración de mocks
        when(directorRepository.existsById(ID)).thenReturn(false);

        // Ejecución del método
        try {
            directorService.updateDirector(ID, directorBO);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Director not found", e.getMessage());
        }
    }

    /**
     * Test update director update failed.
     */
    @Test
    @DisplayName("Test update director with director failed")
    void testUpdateDirector_updateFailed() {
        // Configuración de mocks
        when(directorRepository.existsById(ID)).thenReturn(true);
        when(directorBOMapper.boToEntity(directorBO)).thenReturn(new Director());
        when(directorRepository.save(any(Director.class))).thenThrow(new RuntimeException("Error updating director"));

        // Ejecución del método
        try {
            directorService.updateDirector(ID, directorBO);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Failed to update director: ", e.getMessage());
        }
    }

    @Test
    @DisplayName("Tets delete director with director not found")
    void testDeleteDirector_directorNotFound() {
        // Configuración de mocks
        when(directorRepository.existsById(ID)).thenReturn(false);

        // Ejecución del método
        try {
            directorService.deleteDirector(ID);
            fail("Expected RuntimeException");
        } catch (RuntimeException e) {
            assertEquals("Director not found", e.getMessage());
        }
    }

}

