package com.viewnex.bsan.practica04.controller;

import com.viewnex.bsan.practica04.bo.FilmBo;
import com.viewnex.bsan.practica04.service.FilmService;
import com.viewnex.bsan.practica04.util.constants.RestApiPaths;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestApiPaths.BASE_FILMS_PATH)
public class FilmController {

    private final FilmService service;

    public FilmController(FilmService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<FilmBo> getRoot() {
        return getAll();
    }

    @GetMapping("/")
    public List<FilmBo> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public FilmBo getById(@PathVariable long id) {
        return service.getById(id);
    }

}
