package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.model.dto.FilmDTO;
import com.santander.peliculacrud.service.FilmServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.mapper.FilmDTOMapper;
import org.junit.jupiter.api.BeforeEach;
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
 * The type Film controller test.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FilmControllerTest {

    @Mock
    private FilmServiceInterface filmService;
    

    @Mock
    private FilmDTOMapper filmDTOMapper;

    @InjectMocks
    private FilmController filmController;
    
    private FilmDTO filmDTO;
    private  FilmBO filmBO;
    
    
    @BeforeEach
    void setup(){
        
        ActorBO actorBO1 = ActorBO.builder().nation("ESP").name("Actor1").age(19).build();
        ActorBO actorBO2 = ActorBO.builder().nation("ESP").name("Actor2").age(19).build();
        DirectorBO directorBO = DirectorBO.builder().nation("ESP").name("Actor2").age(19).build();
        

        filmDTO = FilmDTO.builder().title("film").created(1930).idActor(List.of(1L, 2L)).idDirector(1L).build();
        filmBO = FilmBO.builder().title("film").created(1930).actors(List.of(actorBO1, actorBO2)).director(directorBO).build();
        
    }

    @Test
    void testCreateFilm() throws Exception {

        when(filmDTOMapper.dtoToBo(filmDTO)).thenReturn(filmBO);
        when(filmService.createFilm(filmBO)).thenReturn(filmBO);

        // Act
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(filmDTO, "filmDTO");
        ResponseEntity<String> response = filmController.createFilm(filmDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Film created successfully", response.getBody());
    }

    @Test
    void testCreateFilm_InvalidRequest() throws Exception {
        // Arrange
        FilmDTO filmDTO = FilmDTO.builder().title(null).created(-1).idActor(null).idDirector(null).build();

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(filmDTO, "filmDTO");


        // Act
        ResponseEntity<String> response = filmController.createFilm(filmDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Film not created", response.getBody());
    }

    @Test
    void testUpdateFilm() throws Exception {
        // Arrange


        when(filmDTOMapper.dtoToBo(filmDTO)).thenReturn(filmBO);
        when(filmService.updateFilm(1L, filmBO)).thenReturn(filmBO);

        // Act
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(filmDTO, "filmDTO");

        ResponseEntity<String> response = filmController.updateFilm(1L, filmDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Film updated successfully", response.getBody());
    }

    @Test
    void testUpdateFilm_InvalidRequest() throws Exception {
        // Arrange
        long id = 1L;
        FilmDTO filmDTO = FilmDTO.builder().title(null).created(-1).idActor(null).idDirector(null).build();

        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(filmDTO, "filmDTO");

        // Act
        ResponseEntity<String> response = filmController.updateFilm(id, filmDTO, bindingResult);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Film not update", response.getBody());
    }

    @Test
    void testDeleteFilm() throws Exception {
        // Arrange
        long id = 1L;
        when(filmService.deleteFilm(id)).thenReturn(true);

        // Act
        ResponseEntity<String> response = filmController.deleteFilm(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Film delete", response.getBody());
    }

    @Test
    void testGetAllFilms() throws Exception {
        // Arrange
        List<FilmBO> filmBOS = Arrays.asList(FilmBO.builder().build(), FilmBO.builder().build());
        List<FilmDTO> filmDTOS = Arrays.asList(FilmDTO.builder().build(), FilmDTO.builder().build());
        when(filmService.getAllFilm()).thenReturn(filmBOS);
        when(filmDTOMapper.bosToDtos(filmBOS)).thenReturn(filmDTOS);
        // Act
        List<FilmDTO> response = filmController.getAllFilms();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
    }

    @Test
    void testGetFilmById() throws Exception {
        // Arrange
        long id = 1L;


        when(filmService.getFilmById(id)).thenReturn(filmBO);
        when(filmDTOMapper.boToDTO(filmBO)).thenReturn(filmDTO);

        // Act
        FilmDTO response = filmController.getFilmById(id);

        // Assert
        assertNotNull(response);
        assertEquals("film", response.getTitle());
        assertEquals(1930, response.getCreated());
    }
    @Test
    void testGetFilmByIdNull() throws Exception {
        // Arrange
        long id = 1L;



        when(filmService.getFilmById(id)).thenReturn(filmBO);
        when(filmDTOMapper.boToDTO(filmBO)).thenReturn(null);

        // Act
        FilmDTO response = filmController.getFilmById(id);

        // Assert
        assertNull(response);
    }

}
