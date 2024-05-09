package com.viewnex.bsan.practica04.controller;

import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.service.DirectorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${practica04.rest-api.paths.directors}")
public class DirectorController {

    private final DirectorService service;

    public DirectorController(DirectorService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<DirectorBo> getRoot() {
        return getAll();
    }

    @GetMapping("/")
    public List<DirectorBo> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public DirectorBo getById(@PathVariable long id) {
        return service.getById(id);
    }

}
