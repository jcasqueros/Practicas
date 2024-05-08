package com.pracs.films.presentation.controllers;

import com.pracs.films.bussiness.services.ProducerService;
import com.pracs.films.exceptions.PresentationException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Producer;
import com.pracs.films.presentation.converters.BoToDtoConverter;
import com.pracs.films.presentation.converters.DtoToBoConverter;
import com.pracs.films.presentation.dto.ProducerDtoIn;
import com.pracs.films.presentation.dto.ProducerDtoOut;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller of {@link Producer}
 *
 * @author Manuel Mateos de Torres
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/producers")
public class ProducerController {

    private final BoToDtoConverter boToDtoConverter;

    private final DtoToBoConverter dtoToBoConverter;

    private final ProducerService producerService;

    /**
     * Method for get all producers
     *
     * @return ResponseEntity<ProducerDtoOut List>
     * @throws ServiceException
     */
    @GetMapping("/findAll")
    public ResponseEntity<List<ProducerDtoOut>> findAll(@RequestParam boolean method) throws ServiceException {
        if (method) {
            try {
                return new ResponseEntity<>(
                        producerService.findAllCriteria().stream().map(boToDtoConverter::producerBoToDtoOut).toList(),
                        HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(
                    producerService.findAll().stream().map(boToDtoConverter::producerBoToDtoOut).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for get a producer by his id.
     *
     * @param id
     * @return ResponseEntity<ProducerDtoOut>
     */
    @GetMapping("/findById/{id}")
    public ResponseEntity<ProducerDtoOut> getById(@RequestParam boolean method, @PathVariable("id") Long id) {
        if (method) {
            try {
                return new ResponseEntity<>(boToDtoConverter.producerBoToDtoOut(producerService.findByIdCriteria(id)),
                        HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(boToDtoConverter.producerBoToDtoOut(producerService.findById(id)),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for create a producer
     *
     * @param producerDtoIn
     * @return ResponseEntity<ProducerDtoOut>
     */
    @Transactional
    @PostMapping("/save")
    public ResponseEntity<ProducerDtoOut> save(@RequestParam boolean method,
            @Valid @RequestBody ProducerDtoIn producerDtoIn) {
        if (method) {
            try {
                return new ResponseEntity<>(boToDtoConverter.producerBoToDtoOut(
                        producerService.saveCriteria(dtoToBoConverter.producerDtoToBo(producerDtoIn))),
                        HttpStatus.CREATED);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(boToDtoConverter.producerBoToDtoOut(
                    producerService.save(dtoToBoConverter.producerDtoToBo(producerDtoIn))), HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for update a producer
     *
     * @param producerDtoIn
     * @return ResponseEntity<ProducerDtoOut>
     */
    @Transactional
    @PutMapping("/update")
    public ResponseEntity<ProducerDtoOut> update(@RequestParam boolean method,
            @Valid @RequestBody ProducerDtoIn producerDtoIn) {
        if (method) {
            try {
                return new ResponseEntity<>(boToDtoConverter.producerBoToDtoOut(
                        producerService.updateCriteria(dtoToBoConverter.producerDtoToBo(producerDtoIn))),
                        HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(boToDtoConverter.producerBoToDtoOut(
                    producerService.update(dtoToBoConverter.producerDtoToBo(producerDtoIn))), HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for delete a producer by his id
     *
     * @param id
     * @return ResponseEntity<Boolean>
     */
    @Transactional
    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestParam boolean method, @RequestParam("id") Long id) {
        if (method) {
            try {
                producerService.deleteByIdCriteria(id);
                return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            producerService.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }
}
