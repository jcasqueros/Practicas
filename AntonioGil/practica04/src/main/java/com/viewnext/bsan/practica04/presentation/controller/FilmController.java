package com.viewnext.bsan.practica04.presentation.controller;

import com.viewnext.bsan.practica04.business.bo.FilmBo;
import com.viewnext.bsan.practica04.business.service.FilmService;
import com.viewnext.bsan.practica04.presentation.dto.FilmReadDto;
import com.viewnext.bsan.practica04.presentation.dto.FilmUpsertDto;
import com.viewnext.bsan.practica04.presentation.request.QueryOptions;
import com.viewnext.bsan.practica04.presentation.request.WatchableFilter;
import com.viewnext.bsan.practica04.util.constants.RestApiPaths;
import com.viewnext.bsan.practica04.util.mapper.ControllerLevelFilmMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * The {@code FilmController} class defines a REST controller for working with films.
 *
 * @author Antonio Gil
 */
@RestController
@RequestMapping(RestApiPaths.BASE_FILMS_PATH)
public class FilmController {

    private final FilmService service;
    private final ControllerLevelFilmMapper mapper;

    public FilmController(FilmService service, ControllerLevelFilmMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public List<FilmReadDto> getRoot(@ModelAttribute WatchableFilter filter,
                                     @ModelAttribute QueryOptions queryOptions) {
        return getAll(filter, queryOptions);
    }

    @GetMapping("/")
    public List<FilmReadDto> getAll(@ModelAttribute WatchableFilter filter,
                                    @ModelAttribute QueryOptions queryOptions) {
        return service.getAll(filter, queryOptions).stream().map(mapper::boToReadDto).toList();
    }

    @GetMapping("/{id}")
    public FilmReadDto getById(@PathVariable long id, @RequestParam Optional<Boolean> useCustomRepository) {
        return mapper.boToReadDto(service.getById(id, useCustomRepository));
    }

    @PostMapping("")
    public ResponseEntity<Void> postRoot(@RequestBody FilmUpsertDto film,
                                         @RequestParam Optional<Boolean> useCustomRepository,
                                         UriComponentsBuilder uriComponentsBuilder) {
        return create(film, useCustomRepository, uriComponentsBuilder);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody FilmUpsertDto film,
                                       @RequestParam Optional<Boolean> useCustomRepository,
                                       UriComponentsBuilder uriComponentsBuilder) {
        FilmBo bo = mapper.dtoToBo(film);
        FilmBo createdBo = service.create(bo, useCustomRepository);

        URI location = uriComponentsBuilder.path(RestApiPaths.BASE_FILMS_PATH)
                .path("/")
                .path(Long.toString(createdBo.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody FilmUpsertDto film,
                                       @RequestParam Optional<Boolean> useCustomRepository) {
        service.update(id, mapper.dtoToBo(film), useCustomRepository);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id, @RequestParam Optional<Boolean> useCustomRepository) {
        service.deleteById(id, useCustomRepository);
        return ResponseEntity.noContent().build();
    }

}
