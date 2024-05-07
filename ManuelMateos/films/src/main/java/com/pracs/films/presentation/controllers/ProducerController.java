package com.pracs.films.presentation.controllers;

import com.pracs.films.bussiness.services.ProducerService;
import com.pracs.films.exceptions.PresentationException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Producer;
import com.pracs.films.presentation.converters.BoToDtoConverter;
import com.pracs.films.presentation.converters.DtoToBoConverter;
import com.pracs.films.presentation.dto.ProducerDtoIn;
import com.pracs.films.presentation.dto.ProducerDtoOut;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller of {@link Producer}
 *
 * @author Manuel Mateos de Torres
 */
@RestController
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
    public ResponseEntity<List<ProducerDtoOut>> findAll() throws ServiceException {
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
    @GetMapping("/getById/{id}")
    public ResponseEntity<ProducerDtoOut> getById(@PathVariable("id") Long id) {
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
    @PostMapping("/save")
    public ResponseEntity<ProducerDtoOut> save(@RequestBody ProducerDtoIn producerDtoIn) {
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
    @PutMapping("/update")
    public ResponseEntity<ProducerDtoOut> update(@RequestBody ProducerDtoIn producerDtoIn) {
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
    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestParam("id") Long id) {
        try {
            producerService.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for get all producers
     *
     * @return ResponseEntity<ProducerDtoOut List>
     * @throws ServiceException
     */
    @GetMapping("/findAllCriteria")
    public ResponseEntity<List<ProducerDtoOut>> findAllCriteria() throws ServiceException {
        try {
            return new ResponseEntity<>(
                    producerService.findAllCriteria().stream().map(boToDtoConverter::producerBoToDtoOut).toList(),
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
    @GetMapping("/getByIdCriteria/{id}")
    public ResponseEntity<ProducerDtoOut> getByIdCriteria(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(boToDtoConverter.producerBoToDtoOut(producerService.findByIdCriteria(id)),
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
    @PostMapping("/saveCriteria")
    public ResponseEntity<ProducerDtoOut> saveCriteria(@RequestBody ProducerDtoIn producerDtoIn) {
        try {
            return new ResponseEntity<>(boToDtoConverter.producerBoToDtoOut(
                    producerService.saveCriteria(dtoToBoConverter.producerDtoToBo(producerDtoIn))), HttpStatus.CREATED);
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
    @PutMapping("/updateCriteria")
    public ResponseEntity<ProducerDtoOut> updateCriteria(@RequestBody ProducerDtoIn producerDtoIn) {
        try {
            return new ResponseEntity<>(boToDtoConverter.producerBoToDtoOut(
                    producerService.updateCriteria(dtoToBoConverter.producerDtoToBo(producerDtoIn))), HttpStatus.OK);
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
    @DeleteMapping("/deleteByIdCriteria")
    public ResponseEntity<Boolean> deleteByIdCriteria(@RequestParam("id") Long id) {
        try {
            producerService.deleteByIdCriteria(id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }
}
