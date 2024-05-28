package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.DirectorRepository;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.dto.UserDTO;
import com.santander.peliculacrud.model.entity.Director;
import com.santander.peliculacrud.util.exception.GenericException;
import com.santander.peliculacrud.util.mapper.bo.DirectorBOMapper;
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
 * The type Director service test.
 */
@ExtendWith(MockitoExtension.class)
class DirectorServiceTest {

    @InjectMocks
    private DirectorService directorService;

    @Mock
    private DirectorBOMapper directorBOMapper;

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private EndpointService endpointService;

    private DirectorBO directorBO;

    private List<DirectorBO> DirectorBOs;

    private Director director;

    private static final long ID = 1;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        DirectorBOs = new ArrayList<>();

        directorBO = DirectorBO.builder().name("John Doe").age(30).nation("ESP").build();
        director = Director.builder().name("John Doe").age(30).nation("ESP").build();

        DirectorBOs.add(DirectorBO.builder().name("John Doe 1").age(30).nation("ING").build());
        DirectorBOs.add(DirectorBO.builder().name("John Doe 2").age(30).nation("ESP").build());
        DirectorBOs.add(DirectorBO.builder().name("John Doe 3").age(30).nation("FRA").build());
        DirectorBOs.add(DirectorBO.builder().name("John Doe 4").age(30).nation("ESP").build());

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
        when(endpointService.getUserByNameAndAge(directorBO.getName(), directorBO.getAge())).thenReturn(
                List.of(new UserDTO()));
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
     * Test create director user not found.
     */
    @Test
    @DisplayName("Test create director user not found")
    void testCreateDirector_userNotFound() {
        // Configuración de mocks
        when(directorBOMapper.boToEntity(directorBO)).thenReturn(new Director());
        when(endpointService.getUserByNameAndAge(directorBO.getName(), directorBO.getAge())).thenReturn(
                Collections.emptyList());

        // Ejecución del método y verificación de la excepción
        GenericException exception = assertThrows(GenericException.class,
                () -> directorService.createDirector(directorBO));
        assertEquals("Failed to create director: ", exception.getMessage());
    }

    /**
     * Test create director repository exception.
     */
    @Test
    @DisplayName("Test create director repository exception")
    void testCreateDirector_repositoryException() {
        // Configuración de mocks
        when(directorBOMapper.boToEntity(directorBO)).thenReturn(new Director());
        when(endpointService.getUserByNameAndAge(directorBO.getName(), directorBO.getAge())).thenReturn(
                List.of(new UserDTO()));
        when(directorRepository.save(any(Director.class))).thenThrow(
                new RuntimeException("Failed to create director: "));

        // Ejecución del método y verificación de la excepción
        GenericException exception = assertThrows(GenericException.class,
                () -> directorService.createDirector(directorBO));
        assertEquals("Failed to create director: ", exception.getMessage());
    }

    /**
     * Test get all directors with data.
     */
    @Test
    @DisplayName("Test get all directors with data")
    void testGetAllDirectors_withData() {
        // Configuración de mocks
        Page<Director> directorsPage = new PageImpl<>(List.of());
        when(directorRepository.findAll(any(Pageable.class))).thenReturn(directorsPage);
        when(directorBOMapper.listEntitytoListBo(directorsPage)).thenReturn(DirectorBOs);

        // Ejecución del método
        List<DirectorBO> result = directorService.getAllDirectors(0);

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
     * Test get all directors with no data.
     */
    @Test
    @DisplayName("Test get all directors with no data")
    void testGetAllDirectors_withNoData() {
        // Configuración de mocks
        Page<Director> directorsPage = new PageImpl<>(Collections.emptyList());
        when(directorRepository.findAll(any(Pageable.class))).thenReturn(directorsPage);
        when(directorBOMapper.listEntitytoListBo(directorsPage)).thenReturn(Collections.emptyList());

        // Ejecución del método
        List<DirectorBO> result = directorService.getAllDirectors(0);

        // Verificaciones
        assertEquals(0, result.size());
    }

    /**
     * Test get all directors repository exception.
     */
    @Test
    @DisplayName("Test get all directors with repository exception")
    void testGetAllDirectors_repositoryException() {
        // Configuración de mocks
        when(directorRepository.findAll(any(Pageable.class))).thenThrow(
                new RuntimeException("Failed to get all directors"));

        // Ejecución del método y verificación de la excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> directorService.getAllDirectors(0));
        assertEquals("Failed to get all directors", exception.getMessage());
    }

    /**
     * Test get director by id valid id.
     *
     * @throws GenericException
     *         the generic exception
     */
    @Test
    @DisplayName("Test get director with valid id")
    void testGetDirectorById_validId() throws GenericException {
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
     *
     * @throws GenericException
     *         the generic exception
     */
    @Test
    @DisplayName("Test update director with director null")
    void testUpdateDirector_directorFound() throws GenericException {
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
     * Test update director not found.
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
        } catch (RuntimeException | GenericException e) {
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
        } catch (RuntimeException | GenericException e) {
            assertEquals("Failed to update director: ", e.getMessage());
        }
    }

    /**
     * Test delete director director not found.
     */
    @Test
    @DisplayName("Tets delete director with director not found")
    void testDeleteDirector_directorNotFound() {
        // Configuración de mocks
        when(directorRepository.existsById(ID)).thenReturn(false);

        // Ejecución del método
        try {
            directorService.deleteDirector(ID);
            fail("Expected RuntimeException");
        } catch (RuntimeException | GenericException e) {
            assertEquals("Director not found", e.getMessage());
        }
    }

    /**
     * Test delete director director found.
     */
    @Test
    @DisplayName("Tets delete director with director found")
    void testDeleteDirector_directorFound() {
        // Configuración de mocks
        when(directorRepository.existsById(ID)).thenReturn(true);
        doNothing().when(directorRepository).deleteById(1L);

        // Ejecución del método
        try {
            boolean deleted = directorService.deleteDirector(ID);
            assertTrue(deleted);
        } catch (RuntimeException | GenericException e) {
            fail("RuntimeException");
        }
    }

    /**
     * Test get director by nation with no data.
     */
    @Test
    @DisplayName("Test getDirectorByNation with no data")
    void testGetDirectorByNation_withNoData() {
        // Configuración de mocks
        String nation = "ESP";
        int page = 0;
        Page<Director> directorsPage = new PageImpl<>(Collections.emptyList());
        when(directorRepository.findAllByNationEquals(eq(nation), any(Pageable.class))).thenReturn(directorsPage);
        when(directorBOMapper.listEntitytoListBo(any(Page.class))).thenReturn(Collections.emptyList());

        // Ejecución del método
        List<DirectorBO> result = directorService.getDirectorByNation(nation, page);

        // Verificaciones
        assertEquals(0, result.size());
    }

    /**
     * Test get director by age with no data.
     */
    @Test
    @DisplayName("Test getDirectorByAge with no data")
    void testGetDirectorByAge_withNoData() {
        // Configuración de mocks
        int age = 30;
        int page = 0;
        Page<Director> directorsPage = new PageImpl<>(Collections.emptyList());
        when(directorRepository.findAllByAgeEquals(eq(age), any(Pageable.class))).thenReturn(directorsPage);
        when(directorBOMapper.listEntitytoListBo(directorsPage)).thenReturn(Collections.emptyList());

        // Ejecución del método
        List<DirectorBO> result = directorService.getDirectorByAge(age, page);

        // Verificaciones
        assertEquals(0, result.size());
    }

    /**
     * Test get director by age with multiple pages.
     */
    @Test
    @DisplayName("Test getDirectorByAge with multiple pages")
    void testGetDirectorByAge_withMultiplePages() {
        // Configuración de mocks
        int age = 30;
        List<Director> directorsListPage1 = Arrays.asList(
                Director.builder().name("PRV Doe 4").age(age).nation("ESP").build(),
                Director.builder().name("PRV Doe 1").age(age).nation("ING").build(),
                Director.builder().name("PRV Doe 2").age(age).nation("ESP").build(),
                Director.builder().name("PRV Doe 3").age(age).nation("FRA").build(),
                Director.builder().name("PRV Doe").age(age).nation("ESP").build());
        List<Director> directorsListPage2 = Arrays.asList(
                Director.builder().name("PRV Doe 5").age(age).nation("ESP").build(),
                Director.builder().name("PRV Doe 6").age(age).nation("ING").build());
        Page<Director> directorsPage1 = new PageImpl<>(directorsListPage1);
        Page<Director> directorsPage2 = new PageImpl<>(directorsListPage2);
        when(directorRepository.findAllByAgeEquals(age, PageRequest.of(0, 5))).thenReturn(directorsPage1);
        when(directorRepository.findAllByAgeEquals(age, PageRequest.of(1, 5))).thenReturn(directorsPage2);
        when(directorBOMapper.listEntitytoListBo(directorsPage1)).thenReturn(directorsListPage1.stream()
                .map(director -> DirectorBO.builder().name(director.getName()).age(director.getAge())
                        .nation(director.getNation()).build()).collect(Collectors.toList()));
        when(directorBOMapper.listEntitytoListBo(directorsPage2)).thenReturn(directorsListPage2.stream()
                .map(director -> DirectorBO.builder().name(director.getName()).age(director.getAge())
                        .nation(director.getNation()).build()).collect(Collectors.toList()));

        // Ejecución del método
        List<DirectorBO> resultPage1 = directorService.getDirectorByAge(age, 0);
        List<DirectorBO> resultPage2 = directorService.getDirectorByAge(age, 1);

        // Verificaciones
        assertEquals(5, resultPage1.size());
        assertEquals(2, resultPage2.size());
    }

    /**
     * Test get director by age with data.
     */
    @Test
    @DisplayName("Test getDirectorByAge with data")
    void testGetDirectorByAge_withData() {
        // Configuración de mocks
        int age = 30;
        int page = 0;
        List<Director> directorsList = Arrays.asList(
                Director.builder().name("PRV Doe 4").age(age).nation("ESP").build(),
                Director.builder().name("PRV Doe 1").age(age).nation("ING").build(),
                Director.builder().name("PRV Doe 2").age(age).nation("ESP").build(),
                Director.builder().name("PRV Doe 3").age(age).nation("FRA").build(),
                Director.builder().name("PRV Doe").age(age).nation("ESP").build());
        Page<Director> directorsPage = new PageImpl<>(directorsList);
        when(directorRepository.findAllByAgeEquals(eq(age), any(Pageable.class))).thenReturn(directorsPage);
        when(directorBOMapper.listEntitytoListBo(directorsPage)).thenReturn(directorsList.stream()
                .map(director -> DirectorBO.builder().name(director.getName()).age(director.getAge())
                        .nation(director.getNation()).build()).collect(Collectors.toList()));

        // Ejecución del método
        List<DirectorBO> result = directorService.getDirectorByAge(age, page);

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
     * Test get director by name with multiple pages.
     */
    @Test
    @DisplayName("Test getDirectorByName with multiple pages")
    void testGetDirectorByName_withMultiplePages() {
        // Configuración de mocks
        String name = "PRV Doe";
        List<Director> directorsListPage1 = Arrays.asList(Director.builder().name(name).age(30).nation("ESP").build(),
                Director.builder().name(name).age(31).nation("ING").build(),
                Director.builder().name(name).age(32).nation("ESP").build(),
                Director.builder().name(name).age(33).nation("FRA").build(),
                Director.builder().name(name).age(34).nation("ESP").build());
        List<Director> directorsListPage2 = Arrays.asList(Director.builder().name(name).age(35).nation("ESP").build(),
                Director.builder().name(name).age(36).nation("ING").build());
        Page<Director> directorsPage1 = new PageImpl<>(directorsListPage1);
        Page<Director> directorsPage2 = new PageImpl<>(directorsListPage2);
        when(directorRepository.findAllByName(name, PageRequest.of(0, 5))).thenReturn(directorsPage1);
        when(directorRepository.findAllByName(name, PageRequest.of(1, 5))).thenReturn(directorsPage2);
        when(directorBOMapper.listEntitytoListBo(directorsPage1)).thenReturn(directorsListPage1.stream()
                .map(director -> DirectorBO.builder().name(director.getName()).age(director.getAge())
                        .nation(director.getNation()).build()).collect(Collectors.toList()));
        when(directorBOMapper.listEntitytoListBo(directorsPage2)).thenReturn(directorsListPage2.stream()
                .map(director -> DirectorBO.builder().name(director.getName()).age(director.getAge())
                        .nation(director.getNation()).build()).collect(Collectors.toList()));

        // Ejecución del método
        List<DirectorBO> resultPage1 = directorService.getDirectorByName(name, 0);
        List<DirectorBO> resultPage2 = directorService.getDirectorByName(name, 1);

        // Verificaciones
        assertEquals(5, resultPage1.size());
        assertEquals(2, resultPage2.size());
    }

}

