package com.pracs.films.presentation.controllers;

import com.pracs.films.bussiness.services.SerieService;
import com.pracs.films.exceptions.PresentationException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Serie;
import com.pracs.films.presentation.converters.BoToDtoConverter;
import com.pracs.films.presentation.converters.DtoToBoConverter;
import com.pracs.films.presentation.dto.SerieDtoIn;
import com.pracs.films.presentation.dto.SerieDtoOut;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller of {@link Serie}
 *
 * @author Manuel Mateos de Torres
 */
@RestController
@Validated
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
    public ResponseEntity<List<SerieDtoOut>> findAll(@RequestParam boolean method,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id", value = "Variable for order the list") String sort)
            throws ServiceException {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if (method) {
            try {
                return new ResponseEntity<>(
                        serieService.findAllCriteria(pageable).stream().map(boToDtoConverter::serieBoToDtoOut).toList(),
                        HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(
                    serieService.findAll(pageable).stream().map(boToDtoConverter::serieBoToDtoOut).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for get all films pginated and filtered
     *
     * @param names
     * @param ages
     * @param directors
     * @param producers
     * @param actors
     * @param method
     * @param sort
     * @param order
     * @return
     * @throws ServiceException
     */
    @GetMapping("/findAllFilter")
    public ResponseEntity<List<SerieDtoOut>> findAllFilter(@RequestParam(required = false) List<String> names,
            @RequestParam(required = false) List<Integer> ages, @RequestParam(required = false) List<String> directors,
            @RequestParam(required = false) List<String> producers, @RequestParam(required = false) List<String> actors,
            @RequestParam boolean method,
            @RequestParam(defaultValue = "id", value = "Variable for order the list") String sort,
            @RequestParam(defaultValue = "asc") String order) throws ServiceException {

        Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(0, 5, Sort.by(new Sort.Order(direction, sort)));

        if (method) {
            try {
                return new ResponseEntity<>(
                        serieService.findAllCriteriaFilter(pageable, names, ages, directors, producers, actors).stream()
                                .map(boToDtoConverter::serieBoToDtoOut).toList(), HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(
                    serieService.findAll(pageable).stream().map(boToDtoConverter::serieBoToDtoOut).toList(),
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
    @GetMapping("/findById/{id}")
    public ResponseEntity<SerieDtoOut> getById(@RequestParam boolean method, @PathVariable("id") Long id) {
        if (method) {
            try {
                return new ResponseEntity<>(boToDtoConverter.serieBoToDtoOut(serieService.findByIdCriteria(id)),
                        HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
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
    @Transactional
    @PostMapping("/save")
    public ResponseEntity<SerieDtoOut> save(@RequestParam boolean method, @RequestParam String port,
            @Valid @RequestBody SerieDtoIn serieDtoIn) {
        if (method) {
            try {
                return new ResponseEntity<>(boToDtoConverter.serieBoToDtoOut(
                        serieService.saveCriteria(dtoToBoConverter.serieDtoToBo(serieDtoIn), port)),
                        HttpStatus.CREATED);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(boToDtoConverter.serieBoToDtoOut(
                    serieService.save(dtoToBoConverter.serieDtoToBo(serieDtoIn), port)), HttpStatus.CREATED);
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
    @Transactional
    @PutMapping("/update")
    public ResponseEntity<SerieDtoOut> update(@RequestParam boolean method, @Valid @RequestBody SerieDtoIn serieDtoIn) {
        if (method) {
            try {
                return new ResponseEntity<>(boToDtoConverter.serieBoToDtoOut(
                        serieService.updateCriteria(dtoToBoConverter.serieDtoToBo(serieDtoIn))), HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
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
    @Transactional
    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestParam boolean method, @RequestParam("id") Long id) {
        if (method) {
            try {
                serieService.deleteByIdCriteria(id);
                return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            serieService.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }
}
