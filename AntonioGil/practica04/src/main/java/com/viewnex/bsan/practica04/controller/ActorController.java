package com.viewnex.bsan.practica04.controller;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.service.ActorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${practica04.rest-api.paths.actors}")
public class ActorController {

    private final ActorService service;

    public ActorController(ActorService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<ActorBo> getRoot() {
        return getAll();
    }

    @GetMapping("/")
    public List<ActorBo> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ActorBo getById(@PathVariable long id) {
        return service.getById(id);
    }

}
