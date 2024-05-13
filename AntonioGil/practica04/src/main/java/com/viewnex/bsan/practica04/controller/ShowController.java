package com.viewnex.bsan.practica04.controller;

import com.viewnex.bsan.practica04.bo.ShowBo;
import com.viewnex.bsan.practica04.dto.ShowReadDto;
import com.viewnex.bsan.practica04.dto.ShowUpsertDto;
import com.viewnex.bsan.practica04.dto.request.QueryOptionsDto;
import com.viewnex.bsan.practica04.dto.request.WatchableFilterDto;
import com.viewnex.bsan.practica04.service.ShowService;
import com.viewnex.bsan.practica04.util.constants.RestApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(RestApiPaths.BASE_SHOWS_PATH)
public class ShowController {

    private final ShowService service;

    public ShowController(ShowService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<ShowReadDto> getRoot(@ModelAttribute WatchableFilterDto filter,
                                     @ModelAttribute QueryOptionsDto queryOptions) {
        return getAll(filter, queryOptions);
    }

    @GetMapping("/")
    public List<ShowReadDto> getAll(@ModelAttribute WatchableFilterDto filter,
                                    @ModelAttribute QueryOptionsDto queryOptions) {
        // TODO: Implement read query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @GetMapping("/{id}")
    public ShowBo getById(@PathVariable long id, @RequestParam Optional<Boolean> useCustomRepository) {
        // TODO: Implement read query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @PostMapping("")
    public ResponseEntity<Void> postRoot(@RequestBody ShowUpsertDto show,
                                         @RequestBody Optional<Boolean> useCustomRepository) {
        return create(show, useCustomRepository);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody ShowUpsertDto show,
                                       @RequestBody Optional<Boolean> useCustomRepository) {
        // TODO: Implement create query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody ShowUpsertDto newShow,
                                       @RequestBody Optional<Boolean> useCustomRepository) {
        // TODO: Implement update query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id, @RequestBody Optional<Boolean> useCustomRepository) {
        // TODO: Implement delete query
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
