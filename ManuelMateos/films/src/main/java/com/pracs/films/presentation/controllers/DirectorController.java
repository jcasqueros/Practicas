package com.pracs.films.presentation.controllers;

import com.pracs.films.bussiness.services.DirectorService;
import com.pracs.films.exceptions.PresentationException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.presentation.converters.BoToDtoConverter;
import com.pracs.films.presentation.converters.DtoToBoConverter;
import com.pracs.films.presentation.dto.DirectorDtoIn;
import com.pracs.films.presentation.dto.DirectorDtoOut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller of {@link Director}
 *
 * @author Manuel Mateos de Torres
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/directors")
public class DirectorController {

    private final BoToDtoConverter boToDtoConverter;

    private final DtoToBoConverter dtoToBoConverter;

    private final DirectorService directorService;

    /**
     * Method for get all directors
     *
     * @return ResponseEntity<DirectorDtoOut List>
     * @throws ServiceException
     */
    @GetMapping("/findAll")
    public ResponseEntity<List<DirectorDtoOut>> findAll() throws ServiceException {
        try {
            return new ResponseEntity<>(
                    directorService.findAll().stream().map(boToDtoConverter::directorBoToDtoOut).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for get a director by his id.
     *
     * @param id
     * @return ResponseEntity<DirectorDtoOut>
     */
    @GetMapping("/getById/{id}")
    public ResponseEntity<DirectorDtoOut> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(boToDtoConverter.directorBoToDtoOut(directorService.findById(id)),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for create a director
     *
     * @param directorDtoIn
     * @return ResponseEntity<DirectorDtoOut>
     */
    @PostMapping("/save")
    public ResponseEntity<DirectorDtoOut> save(@RequestBody DirectorDtoIn directorDtoIn) {
        try {
            return new ResponseEntity<>(boToDtoConverter.directorBoToDtoOut(
                    directorService.save(dtoToBoConverter.directorDtoToBo(directorDtoIn))), HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for update a director
     *
     * @param directorDtoIn
     * @return ResponseEntity<DirectorDtoOut>
     */
    @PutMapping("/update")
    public ResponseEntity<DirectorDtoOut> update(@RequestBody DirectorDtoIn directorDtoIn) {
        try {
            return new ResponseEntity<>(boToDtoConverter.directorBoToDtoOut(
                    directorService.update(dtoToBoConverter.directorDtoToBo(directorDtoIn))), HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for delete a director by his id
     *
     * @param id
     * @return ResponseEntity<Boolean>
     */
    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestParam("id") Long id) {
        try {
            directorService.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }
}
