package com.pracs.films.presentation.controllers;

import com.pracs.films.bussiness.services.ActorService;
import com.pracs.films.exceptions.PresentationException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Actor;
import com.pracs.films.presentation.converters.BoToDtoConverter;
import com.pracs.films.presentation.converters.DtoToBoConverter;
import com.pracs.films.presentation.dto.ActorDtoIn;
import com.pracs.films.presentation.dto.ActorDtoInUpdate;
import com.pracs.films.presentation.dto.ActorDtoOut;
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
    public ResponseEntity<List<ActorDtoOut>> findAll(@RequestParam boolean method,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id", name = "Variable to order the list") String sort)
            throws ServiceException {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if (method) {
            try {
                return new ResponseEntity<>(
                        actorService.findAllCriteria(pageable).stream().map(boToDtoConverter::actorBoToDtoOut).toList(),
                        HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(
                    actorService.findAll(pageable).stream().map(boToDtoConverter::actorBoToDtoOut).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for get all actors filtered
     *
     * @param names
     * @param ages
     * @param nationalities
     * @param sort
     * @param order
     * @return
     * @throws ServiceException
     */
    @GetMapping("/findAllFilter")
    public ResponseEntity<List<ActorDtoOut>> findAllFilter(@RequestParam(required = false) List<String> names,
            @RequestParam(required = false) List<Integer> ages,
            @RequestParam(required = false) List<String> nationalities,
            @RequestParam(defaultValue = "id", name = "Variable to order the list") String sort,
            @RequestParam(defaultValue = "asc") String order) throws ServiceException {

        Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(0, 5, Sort.by(new Sort.Order(direction, sort)));

        try {
            return new ResponseEntity<>(
                    actorService.findAllCriteriaFilter(pageable, names, ages, nationalities).stream()
                            .map(boToDtoConverter::actorBoToDtoOut).toList(), HttpStatus.OK);
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
     * Method for get an actor by his name.
     *
     * @param name
     * @return ResponseEntity<ActorDtoOut>
     */
    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<ActorDtoOut>> getByName(@PathVariable("name") String name) {
        try {
            return new ResponseEntity<>(
                    actorService.findByName(name).stream().map(boToDtoConverter::actorBoToDtoOut).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    @GetMapping("/findByNameAndAge")
    public ResponseEntity<List<ActorDtoOut>> getByNameAndAge(@RequestParam boolean method, @RequestParam String name,
            @RequestParam int age) {
        if (method) {
            try {
                return new ResponseEntity<>(
                        actorService.findByNameAndAgeCriteria(name, age).stream().map(boToDtoConverter::actorBoToDtoOut)
                                .toList(), HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(
                    actorService.findByNameAndAge(name, age).stream().map(boToDtoConverter::actorBoToDtoOut).toList(),
                    HttpStatus.OK);
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
    public ResponseEntity<ActorDtoOut> update(@RequestParam boolean method,
            @Valid @RequestBody ActorDtoInUpdate actorDtoIn) {
        if (method) {
            try {
                return new ResponseEntity<>(boToDtoConverter.actorBoToDtoOut(
                        actorService.updateCriteria(dtoToBoConverter.actorDtoUpdateToBo(actorDtoIn))), HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(boToDtoConverter.actorBoToDtoOut(
                    actorService.update(dtoToBoConverter.actorDtoUpdateToBo(actorDtoIn))), HttpStatus.OK);
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
