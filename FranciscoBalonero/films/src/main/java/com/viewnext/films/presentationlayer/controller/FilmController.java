package com.viewnext.films.presentationlayer.controller;

import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.FilmService;
import com.viewnext.films.presentationlayer.dto.FilmInDTO;
import com.viewnext.films.presentationlayer.dto.FilmOutDTO;
import com.viewnext.films.presentationlayer.dto.FilmUpdateDTO;
import com.viewnext.films.util.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing films.
 *
 * <p>This class defines RESTful endpoints for performing CRUD operations on films. It handles
 * requests related to retrieving all films, retrieving a film by id, updating film, deleting films by id, and save a
 * film.</p>
 *
 * @author Franciosco Balonero Olivera
 * @see FilmService
 * @see Converter
 */
@RestController
@RequestMapping("api/v1/Film")
@RequiredArgsConstructor
@Validated
public class FilmController {

    private final FilmService filmService;

    private final Converter converter;

    @Operation(summary = "Get all Films")
    @GetMapping("/getAllFilms")
    public ResponseEntity<List<FilmOutDTO>> getAllFilms(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number") int pageNumber,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size") int pageSize,
            @RequestParam(defaultValue = "id") @Parameter(description = "Sort by field") String sortBy,
            @RequestParam(defaultValue = "true") @Parameter(description = "Sort order (false for ascending, true for descending)") boolean sortOrder)
            throws ServiceException {
        if (select) {
            return new ResponseEntity<>(filmService.criteriaGetAll(pageNumber, pageSize, sortBy, sortOrder).stream()
                    .map(converter::filmBOToOutDTO).toList(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(filmService.jpaGetAll(pageNumber, pageSize, sortBy, sortOrder).stream()
                    .map(converter::filmBOToOutDTO).toList(), HttpStatus.OK);
        }
    }

    @Operation(summary = "Get Film by id")
    @GetMapping("/getFilm")
    public ResponseEntity<FilmOutDTO> getFilmById(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestParam @Parameter(description = "Id of the Film") long id) throws ServiceException {
        if (select) {
            return new ResponseEntity<>(converter.filmBOToOutDTO(filmService.criteriaGetById(id)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(converter.filmBOToOutDTO(filmService.jpaGetById(id)), HttpStatus.OK);
        }
    }

    @Operation(summary = "Save a new Film")
    @PostMapping("/save")
    public ResponseEntity<FilmOutDTO> saveFilm(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestBody @Valid FilmInDTO filmInDTO) throws ServiceException {
        if (select) {
            return new ResponseEntity<>(
                    converter.filmBOToOutDTO(filmService.criteriaCreate(converter.filmInDTOToBO(filmInDTO))),
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    converter.filmBOToOutDTO(filmService.jpaCreate(converter.filmInDTOToBO(filmInDTO))),
                    HttpStatus.CREATED);
        }
    }

    @Operation(summary = "Delete a Film")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFilm(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestParam @Parameter(description = "Id of the Film") long id) throws ServiceException {
        if (select) {
            filmService.criteriaDeleteById(id);
        } else {
            filmService.jpaDeleteById(id);
        }
        return new ResponseEntity<>("The film has been successfully deleted ", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a Film")
    @PutMapping("/update")
    public ResponseEntity<FilmOutDTO> updateFilm(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestBody @Valid FilmUpdateDTO filmUpdateDTO) throws ServiceException {
        if (select) {
            return new ResponseEntity<>(
                    converter.filmBOToOutDTO(filmService.criteriaUpdate(converter.filmUpdateDTOToBO(filmUpdateDTO))),
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    converter.filmBOToOutDTO(filmService.jpaUpdate(converter.filmUpdateDTOToBO(filmUpdateDTO))),
                    HttpStatus.CREATED);
        }

    }

    @Operation(summary = "Filter Films")
    @GetMapping("/filterFilms")
    public ResponseEntity<List<FilmOutDTO>> filterFilms(
            @RequestParam(required = false) @Parameter(description = "List of titles to filter by") List<String> titles,
            @RequestParam(required = false) @Parameter(description = "List of release years to filter by") List<Integer> releaseYears,
            @RequestParam(required = false) @Parameter(description = "List of directors to filter by") List<String> directors,
            @RequestParam(required = false) @Parameter(description = "List of producers to filter by") List<String> producers,
            @RequestParam(required = false) @Parameter(description = "List of actors to filter by") List<String> actors,
            @RequestParam(defaultValue = "0") @Parameter(description = "Page number") int pageNumber,
            @RequestParam(defaultValue = "10") @Parameter(description = "Page size") int pageSize,
            @RequestParam(defaultValue = "id") @Parameter(description = "Sort by field") String sortBy,
            @RequestParam(defaultValue = "true") @Parameter(description = "Sort order (false for ascending, true for descending)") boolean sortOrder)
            throws ServiceException {

        return new ResponseEntity<>(
                filmService.filterFilms(titles, releaseYears, directors, producers, actors, pageNumber, pageSize,
                        sortBy, sortOrder).stream().map(converter::filmBOToOutDTO).toList(), HttpStatus.OK);
    }
}