package com.viewnext.bsan.practica04.presentation.controller;

import com.viewnext.bsan.practica04.business.bo.ShowBo;
import com.viewnext.bsan.practica04.business.service.ShowService;
import com.viewnext.bsan.practica04.presentation.dto.ShowReadDto;
import com.viewnext.bsan.practica04.presentation.dto.ShowUpsertDto;
import com.viewnext.bsan.practica04.presentation.request.QueryOptions;
import com.viewnext.bsan.practica04.presentation.request.WatchableFilter;
import com.viewnext.bsan.practica04.util.constants.RestApiPaths;
import com.viewnext.bsan.practica04.util.mapper.ControllerLevelShowMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * The {@code ShowController} class defines a REST controller for working with shows.
 *
 * @author Antonio Gil
 */
@RestController
@RequestMapping(RestApiPaths.BASE_SHOWS_PATH)
public class ShowController {

    private final ShowService service;
    private final ControllerLevelShowMapper mapper;

    public ShowController(ShowService service, ControllerLevelShowMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public List<ShowReadDto> getRoot(@ModelAttribute WatchableFilter filter,
                                     @ModelAttribute QueryOptions queryOptions) {
        return getAll(filter, queryOptions);
    }

    @GetMapping("/")
    public List<ShowReadDto> getAll(@ModelAttribute WatchableFilter filter,
                                    @ModelAttribute QueryOptions queryOptions) {
        return service.getAll(filter, queryOptions).stream().map(mapper::boToReadDto).toList();
    }

    @GetMapping("/{id}")
    public ShowReadDto getById(@PathVariable long id, @RequestParam Optional<Boolean> useCustomRepository) {
        return mapper.boToReadDto(service.getById(id, useCustomRepository));
    }

    @PostMapping("")
    public ResponseEntity<Void> postRoot(@RequestBody ShowUpsertDto show,
                                         @RequestBody Optional<Boolean> useCustomRepository,
                                         UriComponentsBuilder uriComponentsBuilder) {
        return create(show, useCustomRepository, uriComponentsBuilder);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody ShowUpsertDto show,
                                       @RequestBody Optional<Boolean> useCustomRepository,
                                       UriComponentsBuilder uriComponentsBuilder) {
        ShowBo bo = mapper.dtoToBo(show);
        ShowBo createdBo = service.create(bo, useCustomRepository);

        URI location = uriComponentsBuilder.path(RestApiPaths.BASE_SHOWS_PATH)
                .path("/")
                .path(Long.toString(createdBo.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody ShowUpsertDto show,
                                       @RequestBody Optional<Boolean> useCustomRepository) {
        service.update(id, mapper.dtoToBo(show), useCustomRepository);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id, @RequestBody Optional<Boolean> useCustomRepository) {
        service.deleteById(id, useCustomRepository);
        return ResponseEntity.noContent().build();
    }

}
