package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.ActorBO;
import com.santander.peliculacrud.model.dto.ActorDTO;
import com.santander.peliculacrud.service.ActorServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;

import com.santander.peliculacrud.util.exception.GenericException;
import com.santander.peliculacrud.util.mapper.dto.ActorDTOMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

/**
 * The type Actor controller.
 */
@RestController
@RequestMapping("/actors")
public class ActorController {

    private final ActorServiceInterface actorService;
    private final CommonOperation commonOperation;
    private final ActorDTOMapper actorDTOMapper;

    private static final Logger logger = LoggerFactory.getLogger(ActorController.class);

    /**
     * Instantiates a new Actor controller.
     *
     * @param actorService
     *         the actor service
     * @param commonOperation
     *         the common operation
     * @param actorDTOMapper
     *         the actor dto mapper
     */
    @Autowired
    public ActorController(ActorServiceInterface actorService, CommonOperation commonOperation,
            ActorDTOMapper actorDTOMapper) {
        this.actorService = actorService;
        this.commonOperation = commonOperation;
        this.actorDTOMapper = actorDTOMapper;
    }

    /**
     * Create actor response entity.
     *
     * @param actor
     *         the actor
     * @param bindingResult
     *         the binding result
     * @return the response entity
     * @throws GenericException
     *         the generic exception
     */
    @ApiOperation(value = "Create a new actor", notes = "Create a new actor with the provided information")
    @ApiResponses({ @ApiResponse(code = 201, message = "Actor created successfulnesses"),
            @ApiResponse(code = 400, message = "Invalid request") })
    @PostMapping
    public ResponseEntity<String> createActor(@Valid @RequestBody ActorDTO actor, BindingResult bindingResult)
            throws GenericException {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Actor not created";

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            ActorBO actorBO = actorDTOMapper.dtoToBo(actor);
            if (actorService.createActor(actorBO) != null) {
                message = "Actor created successfully";
                status = HttpStatus.CREATED;

            }

        }
        return new ResponseEntity<>(message, status);

    }

    /**
     * Update actor response entity.
     *
     * @param id
     *         the id
     * @param updatedActor
     *         the updated actor
     * @param bindingResult
     *         the binding result
     * @return the response entity
     * @throws GenericException
     *         the generic exception
     */
    @PutMapping("/")
    public ResponseEntity<String> updateActor(@RequestParam @NotNull Long id, @Valid @RequestBody ActorDTO updatedActor,
            BindingResult bindingResult) throws GenericException {
        String message = "Actor not update";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            ActorBO actorBO = actorDTOMapper.dtoToBo(updatedActor);
            if (actorService.updateActor(id, actorBO)) {
                message = "Actor updated successfully";
                status = HttpStatus.OK;

            }
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Delete actor response entity.
     *
     * @param id
     *         the id
     * @return the response entity
     * @throws GenericException
     *         the generic exception
     */
    @DeleteMapping("/")
    public ResponseEntity<String> deleteActor(@RequestParam @NotNull Long id) throws GenericException {
        String message = "User not delete";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (actorService.deleteActor(id)) {
            message = "Actor delete";
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Gets all actors.
     *
     * @param page
     *         the page
     * @return the all actors
     */
    @GetMapping("/all/")
    public ResponseEntity<List<ActorDTO>> getAllActors(@RequestParam(defaultValue = "0") int page) {
        List<ActorBO> actorBOS = actorService.getAllActors(page);
        return ResponseEntity.ok(actorDTOMapper.bosToDtos(actorBOS));
    }

    /**
     * Gets actor by id.
     *
     * @param id
     *         the id
     * @return the actor by id
     * @throws GenericException
     *         the generic exception
     */
    @GetMapping("/")
    public ResponseEntity<ActorDTO> getActorById(@RequestParam @NotNull Long id) throws GenericException {
        ActorBO actorBO = actorService.getActorById(id);
        if (actorBO == null) {
            logger.error("Actor not found");
            return ResponseEntity.notFound().build();
        }
        ActorDTO actorDTO = actorDTOMapper.boToDto(actorBO);
        return ResponseEntity.ok(actorDTO);
    }

    /**
     * Gets actors by age.
     *
     * @param age
     *         the age
     * @param page
     *         the page
     * @return the actors by age
     */
    @GetMapping("/by-age/")
    public ResponseEntity<List<ActorDTO>> getActorsByAge(@RequestParam int age,
            @RequestParam(defaultValue = "0") int page) {
        List<ActorBO> actorBOs = actorService.getActorByAge(age, page);
        if (actorBOs.isEmpty()) {
            logger.error("No actors found with age {}", age);
            return ResponseEntity.notFound().build();
        }
        List<ActorDTO> actorDTOS = actorDTOMapper.bosToDtos(actorBOs);
        return ResponseEntity.ok(actorDTOS);
    }

    /**
     * Gets actors by name.
     *
     * @param name
     *         the name
     * @param page
     *         the page
     * @return the actors by name
     */
    @GetMapping("/by-name/")
    public ResponseEntity<List<ActorDTO>> getActorsByName(@RequestParam String name,
            @RequestParam(defaultValue = "0") int page) {
        List<ActorBO> actorBOs = actorService.getActorByName(name, page);
        if (actorBOs.isEmpty()) {
            logger.error("No actors found with name {}", name);
            return ResponseEntity.notFound().build();
        }
        List<ActorDTO> actorDTOS = actorDTOMapper.bosToDtos(actorBOs);
        return ResponseEntity.ok(actorDTOS);
    }

    /**
     * Gets actors by nation.
     *
     * @param nation
     *         the nation
     * @param page
     *         the page
     * @return the actors by nation
     */
    @GetMapping("/by-nation/")
    public ResponseEntity<List<ActorDTO>> getActorsByNation(@RequestParam String nation,
            @RequestParam(defaultValue = "0") int page) {
        List<ActorBO> actorBOs = actorService.getActorByNation(nation, page);
        if (actorBOs.isEmpty()) {
            logger.error("No actors found with nation {}", nation);
            return ResponseEntity.notFound().build();
        }
        List<ActorDTO> actorDTOS = actorDTOMapper.bosToDtos(actorBOs);
        return ResponseEntity.ok(actorDTOS);
    }


    @GetMapping("/by-all-filter/")
    public ResponseEntity<List<ActorDTO>> getActorsByAllFilter(@RequestParam List<String> name,@RequestParam List<Integer> age,@RequestParam List<String> nation,
            @RequestParam(defaultValue = "0") int page) {
        List<ActorBO> actorBOs = actorService.getActorByAllFilter(name, age, nation, page);
        if (actorBOs.isEmpty()) {
            logger.error("No actors found with nation {}, name {}, age {}", nation, name , age);
            return ResponseEntity.notFound().build();
        }
        List<ActorDTO> actorDTOS = actorDTOMapper.bosToDtos(actorBOs);
        return ResponseEntity.ok(actorDTOS);
    }

}
