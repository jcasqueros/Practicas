package com.viewnext.films.presentationlayer.controller;

import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.ActorService;
import com.viewnext.films.presentationlayer.dto.ActorInDTO;
import com.viewnext.films.presentationlayer.dto.ActorOutDTO;
import com.viewnext.films.presentationlayer.dto.ActorUpdateDTO;
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
 * Controller class for managing actors.
 *
 * <p>This class defines RESTful endpoints for performing CRUD operations on actors. It handles
 * requests related to retrieving all actors, retrieving a actor by id, updating actor, deleting actors by id, and save
 * a actor.</p>
 *
 * @author Franciosco Balonero Olivera
 * @see ActorService
 * @see Converter
 */
@RestController
@RequestMapping("api/v1/Actor")
@RequiredArgsConstructor
@Validated
public class ActorController {

    private final ActorService actorService;

    private final Converter converter;

    @Operation(summary = "Get all Actors")
    @GetMapping("/getAllActors")
    public ResponseEntity<List<ActorOutDTO>> getAllActors(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select)
            throws ServiceException {

        if (select) {
            return new ResponseEntity<>(actorService.criteriaGetAll().stream().map(converter::actorBOToOutDTO).toList(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(actorService.jpaGetAll().stream().map(converter::actorBOToOutDTO).toList(),
                    HttpStatus.OK);
        }

    }

    @Operation(summary = "Get Actor by id")
    @GetMapping("/getActor")
    public ResponseEntity<ActorOutDTO> getActorById(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestParam @Parameter(description = "Id of the Actor") long id) throws ServiceException {

        if (select) {
            return new ResponseEntity<>(converter.actorBOToOutDTO(actorService.criteriaGetById(id)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(converter.actorBOToOutDTO(actorService.jpaGetById(id)), HttpStatus.OK);
        }

    }

    @Operation(summary = "Save a new Actor")
    @PostMapping("/save")
    public ResponseEntity<ActorOutDTO> saveActor(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestBody @Valid ActorInDTO actorInDTO) throws ServiceException {

        if (select) {
            return new ResponseEntity<>(
                    converter.actorBOToOutDTO(actorService.criteriaCreate(converter.actorInDTOToBO(actorInDTO))),
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    converter.actorBOToOutDTO(actorService.jpaCreate(converter.actorInDTOToBO(actorInDTO))),
                    HttpStatus.CREATED);
        }

    }

    @Operation(summary = "Delete a Actor")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteActor(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestParam @Parameter(description = "Id of the Actor") long id) throws ServiceException {

        if (select) {
            actorService.criteriaDeleteById(id);
        } else {
            actorService.jpaDeleteById(id);
        }

        return new ResponseEntity<>("The actor has been successfully deleted ", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a Actor")
    @PutMapping("/update")
    public ResponseEntity<ActorOutDTO> updateActor(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestBody @Valid ActorUpdateDTO actorUpdateDTO) throws ServiceException {

        if (select) {
            return new ResponseEntity<>(converter.actorBOToOutDTO(
                    actorService.criteriaUpdate(converter.actorUpdateDTOToBO(actorUpdateDTO))), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    converter.actorBOToOutDTO(actorService.jpaUpdate(converter.actorUpdateDTOToBO(actorUpdateDTO))),
                    HttpStatus.CREATED);
        }

    }
}