package com.viewnext.bsan.practica04.presentation.controller;

import com.viewnext.bsan.practica04.business.bo.ActorBo;
import com.viewnext.bsan.practica04.business.service.ActorService;
import com.viewnext.bsan.practica04.presentation.dto.ActorReadDto;
import com.viewnext.bsan.practica04.presentation.dto.ActorUpsertDto;
import com.viewnext.bsan.practica04.presentation.request.PersonFilter;
import com.viewnext.bsan.practica04.presentation.request.QueryOptions;
import com.viewnext.bsan.practica04.util.constants.RestApiPaths;
import com.viewnext.bsan.practica04.util.mapper.ControllerLevelActorMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
    private final ControllerLevelActorMapper mapper;

    public ActorController(ActorService service, ControllerLevelActorMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public List<ActorReadDto> getRoot(@ModelAttribute PersonFilter filter, @ModelAttribute QueryOptions queryOptions) {
        return getAll(filter, queryOptions);
    }

    @GetMapping("/")
    public List<ActorReadDto> getAll(@ModelAttribute PersonFilter filter, @ModelAttribute QueryOptions queryOptions) {
        return service.getAll(filter, queryOptions).stream().map(mapper::boToReadDto).toList();
    }

    @GetMapping("/{id}")
    public ActorReadDto getById(@PathVariable long id, @RequestParam Optional<Boolean> useCustomRepository) {
        return mapper.boToReadDto(service.getById(id, useCustomRepository));
    }

    @PostMapping("")
    public ResponseEntity<Void> postRoot(@RequestBody ActorUpsertDto actor,
                                         @RequestParam Optional<Boolean> useCustomRepository,
                                         UriComponentsBuilder uriComponentsBuilder) {
        return create(actor, useCustomRepository, uriComponentsBuilder);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody ActorUpsertDto actor,
                                       @RequestParam Optional<Boolean> useCustomRepository,
                                       UriComponentsBuilder uriComponentsBuilder) {
        ActorBo bo = mapper.dtoToBo(actor);
        ActorBo createdBo = service.create(bo, useCustomRepository);

        URI location = uriComponentsBuilder.path(RestApiPaths.BASE_ACTORS_PATH)
                .path("/")
                .path(Long.toString(createdBo.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody ActorUpsertDto actor,
                                       @RequestParam Optional<Boolean> useCustomRepository) {
        service.update(id, mapper.dtoToBo(actor), useCustomRepository);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id, @RequestParam Optional<Boolean> useCustomRepository) {
        service.deleteById(id, useCustomRepository);
        return ResponseEntity.noContent().build();
    }

}
