package com.viewnex.bsan.practica04.controller;

import com.viewnex.bsan.practica04.bo.ActorBo;
import com.viewnex.bsan.practica04.service.ActorService;
import com.viewnex.bsan.practica04.util.constants.RestApiPaths;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestApiPaths.BASE_ACTORS_PATH)
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

    @PostMapping("")
    public ActorBo postRoot(@RequestBody ActorBo actor) {
        return create(actor);
    }

    @PostMapping("/")
    public ActorBo create(@RequestBody ActorBo actor) {
        return service.create(actor);
    }

}
