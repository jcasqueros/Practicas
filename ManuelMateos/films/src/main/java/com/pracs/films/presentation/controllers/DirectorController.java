package com.pracs.films.presentation.controllers;

import com.pracs.films.bussiness.services.DirectorService;
import com.pracs.films.exceptions.PresentationException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.presentation.converters.BoToDtoConverter;
import com.pracs.films.presentation.converters.DtoToBoConverter;
import com.pracs.films.presentation.dto.DirectorDtoIn;
import com.pracs.films.presentation.dto.DirectorDtoOut;
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
 * Controller of {@link Director}
 *
 * @author Manuel Mateos de Torres
 */
@RestController
@Validated
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
    public ResponseEntity<List<DirectorDtoOut>> findAll(@RequestParam boolean method,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id", value = "Variable for order the list") String sort)
            throws ServiceException {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if (method) {
            try {
                return new ResponseEntity<>(
                        directorService.findAllCriteria(pageable).stream().map(boToDtoConverter::directorBoToDtoOut)
                                .toList(), HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(
                    directorService.findAll(pageable).stream().map(boToDtoConverter::directorBoToDtoOut).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for get all directors paginated and filtered
     *
     * @param names
     * @param ages
     * @param nationalities
     * @param method
     * @param sort
     * @param order
     * @return
     * @throws ServiceException
     */
    @GetMapping("/findAllFilter")
    public ResponseEntity<List<DirectorDtoOut>> findAllFilter(@RequestParam(required = false) List<String> names,
            @RequestParam(required = false) List<Integer> ages,
            @RequestParam(required = false) List<String> nationalities, @RequestParam boolean method,
            @RequestParam(defaultValue = "id", value = "Variable for order the list") String sort,
            @RequestParam(defaultValue = "asc") String order) throws ServiceException {

        Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(0, 5, Sort.by(new Sort.Order(direction, sort)));

        if (method) {
            try {
                return new ResponseEntity<>(
                        directorService.findAllCriteriaFilter(pageable, names, ages, nationalities).stream()
                                .map(boToDtoConverter::directorBoToDtoOut).toList(), HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            return new ResponseEntity<>(
                    directorService.findAll(pageable).stream().map(boToDtoConverter::directorBoToDtoOut).toList(),
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
    @GetMapping("/findById/{id}")
    public ResponseEntity<DirectorDtoOut> getById(@RequestParam boolean method, @PathVariable("id") Long id) {
        if (method) {
            try {
                return new ResponseEntity<>(boToDtoConverter.directorBoToDtoOut(directorService.findByIdCriteria(id)),
                        HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
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
    @Transactional
    @PostMapping("/save")
    public ResponseEntity<DirectorDtoOut> save(@RequestParam boolean method,
            @Valid @RequestBody DirectorDtoIn directorDtoIn) {
        if (method) {
            try {
                return new ResponseEntity<>(boToDtoConverter.directorBoToDtoOut(
                        directorService.saveCriteria(dtoToBoConverter.directorDtoToBo(directorDtoIn))),
                        HttpStatus.CREATED);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
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
    @Transactional
    @PutMapping("/update")
    public ResponseEntity<DirectorDtoOut> update(@RequestParam boolean method,
            @Valid @RequestBody DirectorDtoIn directorDtoIn) {
        if (method) {
            try {
                return new ResponseEntity<>(boToDtoConverter.directorBoToDtoOut(
                        directorService.updateCriteria(dtoToBoConverter.directorDtoToBo(directorDtoIn))),
                        HttpStatus.OK);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
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
    @Transactional
    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestParam boolean method, @RequestParam("id") Long id) {
        if (method) {
            try {
                directorService.deleteByIdCriteria(id);
                return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
            } catch (ServiceException e) {
                throw new PresentationException(e.getLocalizedMessage());
            }
        }
        try {
            directorService.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

}
