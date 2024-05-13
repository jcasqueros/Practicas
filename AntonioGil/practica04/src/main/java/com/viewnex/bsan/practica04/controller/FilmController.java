package com.viewnex.bsan.practica04.controller;

import com.viewnex.bsan.practica04.dto.FilmReadDto;
import com.viewnex.bsan.practica04.dto.FilmUpsertDto;
import com.viewnex.bsan.practica04.dto.request.QueryOptionsDto;
import com.viewnex.bsan.practica04.dto.request.WatchableFilterDto;
import com.viewnex.bsan.practica04.service.FilmService;
import com.viewnex.bsan.practica04.util.constants.RestApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(RestApiPaths.BASE_FILMS_PATH)
public class FilmController {

    private final FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<FilmReadDto> getRoot(@ModelAttribute WatchableFilterDto filter,
                                     @ModelAttribute QueryOptionsDto queryOptions) {
        return getAll(filter, queryOptions);
    }

    @GetMapping("/")
    public List<FilmReadDto> getAll(@ModelAttribute WatchableFilterDto filter,
                                    @ModelAttribute QueryOptionsDto queryOptions) {
        // TODO: Implement read query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @GetMapping("/{id}")
    public FilmReadDto getById(@PathVariable long id, @RequestParam Optional<Boolean> useCustomRepository) {
        // TODO: Implement read query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @PostMapping("")
    public ResponseEntity<Void> postRoot(@RequestBody FilmUpsertDto film,
                                         @RequestParam Optional<Boolean> useCustomRepository) {
        return create(film, useCustomRepository);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody FilmUpsertDto film,
                                       @RequestParam Optional<Boolean> useCustomRepository) {
        // TODO: Implement create query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody FilmUpsertDto film,
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
