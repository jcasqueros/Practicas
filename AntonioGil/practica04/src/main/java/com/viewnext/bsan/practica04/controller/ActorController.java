package com.viewnext.bsan.practica04.controller;

import com.viewnext.bsan.practica04.dto.ActorReadDto;
import com.viewnext.bsan.practica04.dto.ActorUpsertDto;
import com.viewnext.bsan.practica04.dto.request.PersonFilterDto;
import com.viewnext.bsan.practica04.dto.request.QueryOptionsDto;
import com.viewnext.bsan.practica04.service.ActorService;
import com.viewnext.bsan.practica04.util.constants.RestApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ActorController} class defines a REST controller for working with actors.
 *
 * @author Antonio Gil
 */
@RestController
@RequestMapping(RestApiPaths.BASE_ACTORS_PATH)
public class ActorController {

    private final ActorService service;

    public ActorController(ActorService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<ActorReadDto> getRoot(@ModelAttribute PersonFilterDto filter,
                                      @ModelAttribute QueryOptionsDto queryOptions) {
        // TODO: Implement read query with options
        return getAll(filter, queryOptions);
    }

    @GetMapping("/")
    public List<ActorReadDto> getAll(@ModelAttribute PersonFilterDto filter,
                                     @ModelAttribute QueryOptionsDto queryOptions) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @GetMapping("/{id}")
    public ActorReadDto getById(@PathVariable long id, @RequestParam Optional<Boolean> useCustomRepository) {
        // TODO: Implement read query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @PostMapping("")
    public ResponseEntity<Void> postRoot(@RequestBody ActorUpsertDto actor,
                                         @RequestParam Optional<Boolean> useCustomRepository) {
        return create(actor, useCustomRepository);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody ActorUpsertDto actor,
                                       @RequestParam Optional<Boolean> useCustomRepository) {
        // TODO: Implement create query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody ActorUpsertDto actor,
                                       @RequestParam Optional<Boolean> useCustomRepository) {
        // TODO: Implement update query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id, @RequestParam Optional<Boolean> useCustomRepository) {
        // TODO: Implement delete query
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
