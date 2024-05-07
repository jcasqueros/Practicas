package com.pracs.films.presentation.controllers;

import com.pracs.films.bussiness.services.FilmService;
import com.pracs.films.exceptions.PresentationException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Film;
import com.pracs.films.presentation.converters.BoToDtoConverter;
import com.pracs.films.presentation.converters.DtoToBoConverter;
import com.pracs.films.presentation.dto.FilmDtoIn;
import com.pracs.films.presentation.dto.FilmDtoOut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller of {@link Film}
 *
 * @author Manuel Mateos de Torres
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final BoToDtoConverter boToDtoConverter;

    private final DtoToBoConverter dtoToBoConverter;

    private final FilmService filmService;

    /**
     * Method for get all films
     *
     * @return ResponseEntity<FilmDtoOut List>
     * @throws ServiceException
     */
    @GetMapping("/findAll")
    public ResponseEntity<List<FilmDtoOut>> findAll() throws ServiceException {
        try {
            return new ResponseEntity<>(filmService.findAll().stream().map(boToDtoConverter::filmBoToDtoOut).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for get a film by his id.
     *
     * @param id
     * @return ResponseEntity<FilmDtoOut>
     */
    @GetMapping("/getById/{id}")
    public ResponseEntity<FilmDtoOut> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(boToDtoConverter.filmBoToDtoOut(filmService.findById(id)), HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for create a film
     *
     * @param filmDtoIn
     * @return ResponseEntity<FilmDtoOut>
     */
    @PostMapping("/save")
    public ResponseEntity<FilmDtoOut> save(@RequestBody FilmDtoIn filmDtoIn) {
        try {
            return new ResponseEntity<>(
                    boToDtoConverter.filmBoToDtoOut(filmService.save(dtoToBoConverter.filmDtoToBo(filmDtoIn))),
                    HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for update a film
     *
     * @param filmDtoIn
     * @return ResponseEntity<FilmDtoOut>
     */
    @PutMapping("/update")
    public ResponseEntity<FilmDtoOut> update(@RequestBody FilmDtoIn filmDtoIn) {
        try {
            return new ResponseEntity<>(
                    boToDtoConverter.filmBoToDtoOut(filmService.update(dtoToBoConverter.filmDtoToBo(filmDtoIn))),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for delete a film by his id
     *
     * @param id
     * @return ResponseEntity<Boolean>
     */
    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestParam("id") Long id) {
        try {
            filmService.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }
}
