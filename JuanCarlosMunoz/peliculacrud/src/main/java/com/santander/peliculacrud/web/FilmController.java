package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.model.dto.FilmDTO;
import com.santander.peliculacrud.service.FilmServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;

import com.santander.peliculacrud.util.exception.GenericException;
import com.santander.peliculacrud.util.mapper.dto.FilmDTOMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

/**
 * The type Film controller.
 */
@RestController
@RequestMapping("/films")
public class FilmController {

    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    private final FilmServiceInterface filmService;
    private final CommonOperation commonOperation;
    private final FilmDTOMapper filmDTOMapper;

    /**
     * Instantiates a new Film controller.
     *
     * @param filmService
     *         the film service
     * @param commonOperation
     *         the common operation
     * @param filmDTOMapper
     *         the film dto mapper
     */
    @Autowired
    public FilmController(FilmServiceInterface filmService, CommonOperation commonOperation,
            FilmDTOMapper filmDTOMapper) {
        this.filmService = filmService;
        this.commonOperation = commonOperation;
        this.filmDTOMapper = filmDTOMapper;
    }

    /**
     * Create film response entity.
     *
     * @param film
     *         the film
     * @param bindingResult
     *         the binding result
     * @return the response entity
     * @throws GenericException
     *         the generic exception
     */
    @ApiOperation(value = "Create a new film", notes = "Create a new film with the provided information")
    @ApiResponses({ @ApiResponse(code = 201, message = "Film created successfulnesses"),
            @ApiResponse(code = 400, message = "Invalid request") })
    @PostMapping
    public ResponseEntity<String> createFilm(@Valid @RequestBody FilmDTO film, BindingResult bindingResult)
            throws GenericException {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Film not created";

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            FilmBO filmBO = filmDTOMapper.dtoToBo(film);
            if (filmService.createFilm(filmBO) != null) {
                message = "Film created successfully";
                status = HttpStatus.CREATED;

            }

        }
        return new ResponseEntity<>(message, status);

    }

    /**
     * Update film response entity.
     *
     * @param id
     *         the id
     * @param updatedFilm
     *         the updated film
     * @param bindingResult
     *         the binding result
     * @return the response entity
     * @throws GenericException
     *         the generic exception
     */
    @PutMapping("/")
    public ResponseEntity<String> updateFilm(@RequestParam @NotNull Long id, @Valid @RequestBody FilmDTO updatedFilm,
            BindingResult bindingResult) throws GenericException {
        String message = "Film not update";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            FilmBO filmBO = filmDTOMapper.dtoToBo(updatedFilm);
            if (filmService.updateFilm(id, filmBO) != null) {
                message = "Film updated successfully";
                status = HttpStatus.OK;

            }
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Delete film response entity.
     *
     * @param id
     *         the id
     * @return the response entity
     * @throws GenericException
     *         the generic exception
     */
    @DeleteMapping("/")
    public ResponseEntity<String> deleteFilm(@RequestParam @NotNull Long id) throws GenericException {
        String message = "User not delete";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (filmService.deleteFilm(id)) {
            message = "Film delete";
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Gets film by id.
     *
     * @param id
     *         the id
     * @return the film by id
     */
    @GetMapping("/")
    public FilmDTO getFilmById(@RequestParam @NotNull Long id) {
        FilmBO filmBO = filmService.getFilmById(id);
        FilmDTO filmDTO = filmDTOMapper.boToDTO(filmBO);

        if (filmDTO == null) {
            logger.error("Film not found");
        }
        return filmDTO;

    }

    /**
     * Gets all actors.
     *
     * @param page
     *         the page
     * @return the all actors
     */
    @GetMapping("/all")
    public ResponseEntity<List<FilmDTO>> getAllFilm(@RequestParam(defaultValue = "0") int page) {
        List<FilmBO> filmBOS = filmService.getAllFilm(page);
        return ResponseEntity.ok(filmDTOMapper.bosToDtos(filmBOS));
    }

    /**
     * Gets film by created.
     *
     * @param created
     *         the created
     * @param page
     *         the page
     * @return the film by created
     */
    @GetMapping("/by-created")
    public ResponseEntity<List<FilmDTO>> getFilmByCreated(@RequestParam int created,
            @RequestParam(defaultValue = "0") int page) {
        List<FilmBO> filmBOS = filmService.getFilmByCreated(created, page);
        if (filmBOS.isEmpty()) {
            logger.error("No film found with created {}", created);
            return ResponseEntity.notFound().build();
        }
        List<FilmDTO> filmDTOS = filmDTOMapper.bosToDtos(filmBOS);
        return ResponseEntity.ok(filmDTOS);
    }

    /**
     * Gets film by title.
     *
     * @param title
     *         the title
     * @param page
     *         the page
     * @return the film by title
     */
    @GetMapping("/by-title")
    public ResponseEntity<List<FilmDTO>> getFilmByTitle(@RequestParam String title,
            @RequestParam(defaultValue = "0") int page) {
        List<FilmBO> filmBOS = filmService.getFilmByTitle(title, page);

        if (filmBOS.isEmpty()) {
            logger.error("No film found with name {}", title);
            return ResponseEntity.notFound().build();
        }
        List<FilmDTO> filmDTOS = filmDTOMapper.bosToDtos(filmBOS);
        return ResponseEntity.ok(filmDTOS);
    }

    /**
     * Gets films by actors.
     *
     * @param actorsName
     *         the actors name
     * @param page
     *         the page
     * @return the films by actors
     */
    @GetMapping("/by-actors")
    public ResponseEntity<List<FilmDTO>> getFilmsByActors(@RequestParam List<String> actorsName,
            @RequestParam int page) {
        List<FilmBO> filmBOS = filmService.getFilmByActors(actorsName, page);

        if (filmBOS.isEmpty()) {
            logger.error("No film found with actorsName {}", actorsName);
            return ResponseEntity.notFound().build();
        }
        List<FilmDTO> filmDTOS = filmDTOMapper.bosToDtos(filmBOS);

        return ResponseEntity.ok(filmDTOS);
    }

    /**
     * Gets films by directors.
     *
     * @param directorsName
     *         the directors name
     * @param page
     *         the page
     * @return the films by directors
     */
    @GetMapping("/by-directors")
    public ResponseEntity<List<FilmDTO>> getFilmsByDirectors(@RequestParam List<String> directorsName,
            @RequestParam int page) {
        List<FilmBO> filmBOS = filmService.getFilmByDirectors(directorsName, page);
        if (filmBOS.isEmpty()) {
            logger.error("No film found with directorsName {}", directorsName);
            return ResponseEntity.notFound().build();
        }
        List<FilmDTO> filmDTOS = filmDTOMapper.bosToDtos(filmBOS);

        return ResponseEntity.ok(filmDTOS);
    }

}

