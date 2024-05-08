package com.pracs.films.presentation.controllers;

import com.pracs.films.bussiness.services.ActorService;
import com.pracs.films.exceptions.PresentationException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Actor;
import com.pracs.films.presentation.converters.BoToDtoConverter;
import com.pracs.films.presentation.converters.DtoToBoConverter;
import com.pracs.films.presentation.dto.ActorDtoIn;
import com.pracs.films.presentation.dto.ActorDtoOut;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller of {@link Actor}
 *
 * @author Manuel Mateos de Torres
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorController {

    private final BoToDtoConverter boToDtoConverter;

    private final DtoToBoConverter dtoToBoConverter;

    private final ActorService actorService;

    /**
     * Method for get all actors
     *
     * @return ResponseEntity<ActorDtoOut List>
     * @throws ServiceException
     */
    @GetMapping("/findAll")
    public ResponseEntity<List<ActorDtoOut>> findAll(@RequestParam boolean method) throws ServiceException {
        if (method) {
            try {
                return new ResponseEntity<>(
                        actorService.findAllCriteria().stream().map(boToDtoConverter::actorBoToDtoOut).toList(),
                        HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(actorService.findAll().stream().map(boToDtoConverter::actorBoToDtoOut).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for get an actor by his id.
     *
     * @param id
     * @return ResponseEntity<ActorDtoOut>
     */
    @GetMapping("/findById/{id}")
    public ResponseEntity<ActorDtoOut> getById(@RequestParam boolean method, @PathVariable("id") Long id) {
        if (method) {
            try {
                return new ResponseEntity<>(boToDtoConverter.actorBoToDtoOut(actorService.findByIdCriteria(id)),
                        HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(boToDtoConverter.actorBoToDtoOut(actorService.findById(id)), HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for create an actor
     *
     * @param actorDtoIn
     * @return ResponseEntity<ActorDtoOut>
     */
    @Transactional
    @PostMapping("/save")
    public ResponseEntity<ActorDtoOut> save(@RequestParam boolean method, @Valid @RequestBody ActorDtoIn actorDtoIn) {
        if (method) {
            try {
                return new ResponseEntity<>(boToDtoConverter.actorBoToDtoOut(
                        actorService.saveCriteria(dtoToBoConverter.actorDtoToBo(actorDtoIn))), HttpStatus.CREATED);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(
                    boToDtoConverter.actorBoToDtoOut(actorService.save(dtoToBoConverter.actorDtoToBo(actorDtoIn))),
                    HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for update an actor
     *
     * @param actorDtoIn
     * @return ResponseEntity<ActorDtoOut>
     */
    @Transactional
    @PutMapping("/update")
    public ResponseEntity<ActorDtoOut> update(@RequestParam boolean method, @Valid @RequestBody ActorDtoIn actorDtoIn) {
        if (method) {
            try {
                return new ResponseEntity<>(boToDtoConverter.actorBoToDtoOut(
                        actorService.updateCriteria(dtoToBoConverter.actorDtoToBo(actorDtoIn))), HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(
                    boToDtoConverter.actorBoToDtoOut(actorService.update(dtoToBoConverter.actorDtoToBo(actorDtoIn))),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for delete an actor by his id
     *
     * @param id
     * @return ResponseEntity<Boolean>
     */
    @Transactional
    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestParam boolean method, @RequestParam("id") long id) {
        if (method) {
            try {
                actorService.deleteByIdCriteria(id);
                return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            actorService.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }
}
