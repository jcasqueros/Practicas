package com.viewnext.films.presentationlayer.controller;

import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.DirectorService;
import com.viewnext.films.presentationlayer.dto.DirectorInDTO;
import com.viewnext.films.presentationlayer.dto.DirectorOutDTO;
import com.viewnext.films.presentationlayer.dto.DirectorUpdateDTO;
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
 * Controller class for managing directors.
 *
 * <p>This class defines RESTful endpoints for performing CRUD operations on directors. It handles
 * requests related to retrieving all directors, retrieving a director by id, updating director, deleting directors by
 * id, and save a director.</p>
 *
 * @author Franciosco Balonero Olivera
 * @see DirectorService
 * @see Converter
 */
@RestController
@RequestMapping("api/v1/Director")
@RequiredArgsConstructor
@Validated
public class DirectorController {

    private final DirectorService directorService;

    private final Converter converter;

    @Operation(summary = "Get all Directors")
    @GetMapping("/getAllDirectors")
    public ResponseEntity<List<DirectorOutDTO>> getAllDirectors(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select)
            throws ServiceException {
        if (select) {
            return new ResponseEntity<>(
                    directorService.criteriaGetAll().stream().map(converter::directorBOToOutDTO).toList(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    directorService.jpaGetAll().stream().map(converter::directorBOToOutDTO).toList(), HttpStatus.OK);
        }
    }

    @Operation(summary = "Get Director by id")
    @GetMapping("/getDirector")
    public ResponseEntity<DirectorOutDTO> getDirectorById(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestParam @Parameter(description = "Id of the Director") long id) throws ServiceException {
        if (select) {
            return new ResponseEntity<>(converter.directorBOToOutDTO(directorService.criteriaGetById(id)),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(converter.directorBOToOutDTO(directorService.jpaGetById(id)), HttpStatus.OK);
        }
    }

    @Operation(summary = "Save a new Director")
    @PostMapping("/save")
    public ResponseEntity<DirectorOutDTO> saveDirector(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestBody @Valid DirectorInDTO directorInDTO) throws ServiceException {
        if (select) {
            return new ResponseEntity<>(converter.directorBOToOutDTO(
                    directorService.criteriaCreate(converter.directorInDTOToBO(directorInDTO))), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    converter.directorBOToOutDTO(directorService.jpaCreate(converter.directorInDTOToBO(directorInDTO))),
                    HttpStatus.CREATED);
        }
    }

    @Operation(summary = "Delete a Director")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDirector(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestParam @Parameter(description = "Id of the Director") long id) throws ServiceException {
        if (select) {
            directorService.criteriaDeleteById(id);
        } else {
            directorService.jpaDeleteById(id);
        }
        return new ResponseEntity<>("The director has been successfully deleted ", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a Director")
    @PutMapping("/update")
    public ResponseEntity<DirectorOutDTO> updateDirector(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestBody @Valid DirectorUpdateDTO directorUpdateDTO) throws ServiceException {
        if (select) {
            return new ResponseEntity<>(converter.directorBOToOutDTO(
                    directorService.criteriaUpdate(converter.directorUpdateDTOToBO(directorUpdateDTO))),
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(converter.directorBOToOutDTO(
                    directorService.jpaUpdate(converter.directorUpdateDTOToBO(directorUpdateDTO))), HttpStatus.CREATED);
        }

    }
}