package com.viewnex.bsan.practica04.controller;

import com.viewnex.bsan.practica04.bo.ShowBo;
import com.viewnex.bsan.practica04.service.ShowService;
import com.viewnex.bsan.practica04.util.constants.RestApiPaths;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(RestApiPaths.BASE_SHOWS_PATH)
public class ShowController {

    private final ShowService service;

    public ShowController(ShowService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<ShowBo> getRoot() {
        return getAll();
    }

    @GetMapping("/")
    public List<ShowBo> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ShowBo getById(@PathVariable long id) {
        return service.getById(id);
    }

}
