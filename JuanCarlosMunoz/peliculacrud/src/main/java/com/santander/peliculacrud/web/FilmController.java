package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.FilmBO;
import com.santander.peliculacrud.model.dto.FilmDTO;
import com.santander.peliculacrud.service.FilmServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;

import com.santander.peliculacrud.util.mapper.FilmDTOMapper;
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

    @Autowired
    private FilmServiceInterface filmService;
    @Autowired
    private CommonOperation commonOperation;
    @Autowired
    private FilmDTOMapper filmDTOMapper;

    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    /**
     * Create film response entity.
     *
     * @param film
     *         the film
     * @param bindingResult
     *         the binding result
     * @return the response entity
     */
    @ApiOperation(value = "Create a new film", notes = "Create a new film with the provided information")
    @ApiResponses({ @ApiResponse(code = 201, message = "Film created successfulnesses"),
            @ApiResponse(code = 400, message = "Invalid request") })
    @PostMapping
    public ResponseEntity<String> createFilm(@Valid @RequestBody FilmDTO film, BindingResult bindingResult) {

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
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateFilm(@PathVariable @NotNull Long id, @Valid @RequestBody FilmDTO updatedFilm,
            BindingResult bindingResult) {
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
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFilm(@PathVariable @NotNull Long id) {
        String message = "User not delete";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (filmService.deleteFilm(id)) {
            message = "Film delete";
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Gets all films.
     *
     * @return the all films
     */
    @GetMapping()
    public List<FilmDTO> getAllFilms() {
        List<FilmBO> filmBOS = filmService.getAllFilm();
        return filmDTOMapper.bosToDtos(filmBOS);
    }

    /**
     * Gets film by id.
     *
     * @param id
     *         the id
     * @return the film by id
     */
    @GetMapping("/{id}")
    public FilmDTO getFilmById(@PathVariable @NotNull Long id) {
        FilmBO filmBO = filmService.getFilmById(id);
        FilmDTO filmDTO = filmDTOMapper.boToDTO(filmBO);

        if (filmDTO == null) {
            logger.error("Film not found");
        }
        return filmDTO;

    }
}

