package com.pracs.films.presentation.controllers;

import com.pracs.films.bussiness.services.SerieService;
import com.pracs.films.exceptions.PresentationException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Serie;
import com.pracs.films.presentation.converters.BoToDtoConverter;
import com.pracs.films.presentation.converters.DtoToBoConverter;
import com.pracs.films.presentation.dto.SerieDtoIn;
import com.pracs.films.presentation.dto.SerieDtoOut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller of {@link Serie}
 *
 * @author Manuel Mateos de Torres
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/series")
public class SerieController {

    private final BoToDtoConverter boToDtoConverter;

    private final DtoToBoConverter dtoToBoConverter;

    private final SerieService serieService;

    /**
     * Method for get all series
     *
     * @return ResponseEntity<SerieDtoOut List>
     * @throws ServiceException
     */
    @GetMapping("/findAll")
    public ResponseEntity<List<SerieDtoOut>> findAll() throws ServiceException {
        try {
            return new ResponseEntity<>(serieService.findAll().stream().map(boToDtoConverter::serieBoToDtoOut).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for get a serie by his id.
     *
     * @param id
     * @return ResponseEntity<SerieDtoOut>
     */
    @GetMapping("/getById/{id}")
    public ResponseEntity<SerieDtoOut> getById(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(boToDtoConverter.serieBoToDtoOut(serieService.findById(id)), HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for create a serie
     *
     * @param serieDtoIn
     * @return ResponseEntity<SerieDtoOut>
     */
    @PostMapping("/save")
    public ResponseEntity<SerieDtoOut> save(@RequestBody SerieDtoIn serieDtoIn) {
        try {
            return new ResponseEntity<>(
                    boToDtoConverter.serieBoToDtoOut(serieService.save(dtoToBoConverter.serieDtoToBo(serieDtoIn))),
                    HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for update a serie
     *
     * @param serieDtoIn
     * @return ResponseEntity<SerieDtoOut>
     */
    @PutMapping("/update")
    public ResponseEntity<SerieDtoOut> update(@RequestBody SerieDtoIn serieDtoIn) {
        try {
            return new ResponseEntity<>(
                    boToDtoConverter.serieBoToDtoOut(serieService.update(dtoToBoConverter.serieDtoToBo(serieDtoIn))),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for delete a serie by his id
     *
     * @param id
     * @return ResponseEntity<Boolean>
     */
    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestParam("id") Long id) {
        try {
            serieService.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for get all series
     *
     * @return ResponseEntity<SerieDtoOut List>
     * @throws ServiceException
     */
    @GetMapping("/findAllCriteria")
    public ResponseEntity<List<SerieDtoOut>> findAllCriteria() throws ServiceException {
        try {
            return new ResponseEntity<>(
                    serieService.findAllCriteria().stream().map(boToDtoConverter::serieBoToDtoOut).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for get a serie by his id.
     *
     * @param id
     * @return ResponseEntity<SerieDtoOut>
     */
    @GetMapping("/getByIdCriteria/{id}")
    public ResponseEntity<SerieDtoOut> getByIdCriteria(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(boToDtoConverter.serieBoToDtoOut(serieService.findByIdCriteria(id)),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for create a serie
     *
     * @param serieDtoIn
     * @return ResponseEntity<SerieDtoOut>
     */
    @PostMapping("/saveCriteria")
    public ResponseEntity<SerieDtoOut> saveCriteria(@RequestBody SerieDtoIn serieDtoIn) {
        try {
            return new ResponseEntity<>(boToDtoConverter.serieBoToDtoOut(
                    serieService.saveCriteria(dtoToBoConverter.serieDtoToBo(serieDtoIn))), HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for update a serie
     *
     * @param serieDtoIn
     * @return ResponseEntity<SerieDtoOut>
     */
    @PutMapping("/updateCriteria")
    public ResponseEntity<SerieDtoOut> updateCriteria(@RequestBody SerieDtoIn serieDtoIn) {
        try {
            return new ResponseEntity<>(boToDtoConverter.serieBoToDtoOut(
                    serieService.updateCriteria(dtoToBoConverter.serieDtoToBo(serieDtoIn))), HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for delete a serie by his id
     *
     * @param id
     * @return ResponseEntity<Boolean>
     */
    @DeleteMapping("/deleteByIdCriteria")
    public ResponseEntity<Boolean> deleteByIdCriteria(@RequestParam("id") Long id) {
        try {
            serieService.deleteByIdCriteria(id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }
}
