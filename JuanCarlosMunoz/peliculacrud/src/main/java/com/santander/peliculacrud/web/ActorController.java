/*
package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.dto.ActorDTO;
import com.santander.peliculacrud.service.ActorServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;

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

*/
/**
 * The type Actor controller.
 *//*

@RestController
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private ActorServiceInterface actorService;
    @Autowired
    private CommonOperation commonOperation;

    private static final Logger logger = LoggerFactory.getLogger(ActorController.class);

    */
/**
     * Create actor string.
     *
     * @param actor
     *         the actor
     * @param bindingResult
     *         the binding result
     * @return the string
     *//*

    @ApiOperation(value = "Create a new actor", notes = "Create a new actor with the provided information")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Actor created sucewrwcessfully"),
            @ApiResponse(code = 400, message = "Invalid request")
    })
    @PostMapping
    public ResponseEntity<String> createActor(@Valid @RequestBody ActorDTO actor, BindingResult bindingResult) {

        String message = "Actor created successfully";
        HttpStatus status = HttpStatus.CREATED;

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

            status = HttpStatus.BAD_REQUEST;
            message = "Actor not created";

        } else {
            actorService.createActor(actor);
        }
        return new ResponseEntity<>(message, status);

    }

    */
/**
     * Update actor string.
     *
     * @param id
     *         the id
     * @param updatedActor
     *         the updated actor
     * @param bindingResult
     *         the binding result
     * @return the string
     *//*

    @PutMapping("/{id}")
    public ResponseEntity<String> updateActor(@PathVariable @NotNull Long id, @Valid @RequestBody ActorDTO updatedActor,
            BindingResult bindingResult) {
        String message = "Actor not update";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else if (actorService.updateActor(id, updatedActor)) {
            message = "Actor updated successfully";
            status = HttpStatus.OK;

        }

        return new ResponseEntity<>(message, status);
    }

    */
/**
     * Delete actor string.
     *
     * @param id
     *         the id
     * @return the string
     *//*

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteActor(@PathVariable @NotNull Long id) {
        String message = "User not delete";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (actorService.deleteActor(id)) {
            message = "Actor delete";
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(message, status);
    }

    */
/**
     * Gets all actors.
     *
     * @return the all actors
     *//*

    @GetMapping()
    public List<ActorDTO> getAllActors() {
        return actorService.getAllActors();
    }

    */
/**
     * Gets actor by id.
     *
     * @param id
     *         the id
     * @return the actor by id
     *//*

    @GetMapping("/{id}")
    public ActorDTO getActorById(@PathVariable @NotNull Long id) {

        ActorDTO actorDTO = actorService.getActorById(id);

        if (actorDTO == null) {
            logger.error("Actor not found");
        }
        return actorDTO;

    }
}
*/
