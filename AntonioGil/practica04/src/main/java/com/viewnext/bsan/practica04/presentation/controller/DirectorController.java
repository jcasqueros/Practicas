package com.viewnext.bsan.practica04.presentation.controller;

import com.viewnext.bsan.practica04.business.bo.DirectorBo;
import com.viewnext.bsan.practica04.business.service.DirectorService;
import com.viewnext.bsan.practica04.presentation.dto.DirectorReadDto;
import com.viewnext.bsan.practica04.presentation.dto.DirectorUpsertDto;
import com.viewnext.bsan.practica04.presentation.request.PersonFilter;
import com.viewnext.bsan.practica04.presentation.request.QueryOptions;
import com.viewnext.bsan.practica04.util.constants.RestApiPaths;
import com.viewnext.bsan.practica04.util.mapper.ControllerLevelDirectorMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    private final ControllerLevelDirectorMapper mapper;

    public DirectorController(DirectorService service, ControllerLevelDirectorMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public List<DirectorReadDto> getRoot(@ModelAttribute PersonFilter filter,
                                         @ModelAttribute QueryOptions queryOptions) {
        return getAll(filter, queryOptions);
    }

    @GetMapping("/")
    public List<DirectorReadDto> getAll(@ModelAttribute PersonFilter filter,
                                        @ModelAttribute QueryOptions queryOptions) {
        return service.getAll(filter, queryOptions).stream().map(mapper::boToReadDto).toList();
    }

    @GetMapping("/{id}")
    public DirectorReadDto getById(@PathVariable long id, @RequestParam Optional<Boolean> useCustomRepository) {
        return mapper.boToReadDto(service.getById(id, useCustomRepository));
    }

    @PostMapping("")
    public ResponseEntity<Void> postRoot(@RequestBody DirectorUpsertDto director,
                                         @RequestParam Optional<Boolean> useCustomRepository,
                                         UriComponentsBuilder uriComponentsBuilder) {
        return create(director, useCustomRepository, uriComponentsBuilder);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody DirectorUpsertDto director,
                                       @RequestParam Optional<Boolean> useCustomRepository,
                                       UriComponentsBuilder uriComponentsBuilder) {
        DirectorBo bo = mapper.dtoToBo(director);
        DirectorBo createdBo = service.create(bo, useCustomRepository);

        URI location = uriComponentsBuilder.path(RestApiPaths.BASE_DIRECTORS_PATH)
                .path("/")
                .path(Long.toString(createdBo.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody DirectorUpsertDto director,
                                       @RequestParam Optional<Boolean> useCustomRepository) {
        service.update(id, mapper.dtoToBo(director), useCustomRepository);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id, @RequestParam Optional<Boolean> useCustomRepository) {
        service.deleteById(id, useCustomRepository);
        return ResponseEntity.noContent().build();
    }

}
