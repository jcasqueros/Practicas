package com.viewnext.bsan.practica04.controller;

import com.viewnext.bsan.practica04.dto.DirectorReadDto;
import com.viewnext.bsan.practica04.dto.DirectorUpsertDto;
import com.viewnext.bsan.practica04.util.request.QueryOptions;
import com.viewnext.bsan.practica04.util.request.PersonFilter;
import com.viewnext.bsan.practica04.service.DirectorService;
import com.viewnext.bsan.practica04.util.constants.RestApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The {@code DirectorController} class defines a REST controller for working with directors.
 *
 * @author Antonio Gil
 */
@RestController
@RequestMapping(RestApiPaths.BASE_DIRECTORS_PATH)
public class DirectorController {

    private final DirectorService service;

    public DirectorController(DirectorService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<DirectorReadDto> getRoot(@ModelAttribute PersonFilter filter,
                                         @ModelAttribute QueryOptions queryOptions) {
        return getAll(filter, queryOptions);
    }

    @GetMapping("/")
    public List<DirectorReadDto> getAll(@ModelAttribute PersonFilter filter,
                                        @ModelAttribute QueryOptions queryOptions) {
        // TODO: Implement read query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @GetMapping("/{id}")
    public DirectorReadDto getById(@PathVariable long id, @RequestParam Optional<Boolean> useCustomRepository) {
        // TODO: Implement read query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @PostMapping("")
    public ResponseEntity<Void> postRoot(@RequestBody DirectorUpsertDto director,
                                         @RequestParam Optional<Boolean> useCustomRepository) {
        return create(director, useCustomRepository);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody DirectorUpsertDto director,
                                       @RequestParam Optional<Boolean> useCustomRepository) {
        // TODO: Implement create query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody DirectorUpsertDto director,
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
