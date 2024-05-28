package com.viewnext.bsan.practica03.presentation.controller;

import com.viewnext.bsan.practica03.business.bo.UserBo;
import com.viewnext.bsan.practica03.presentation.dto.UserReadDto;
import com.viewnext.bsan.practica03.presentation.dto.UserUpsertDto;
import com.viewnext.bsan.practica03.business.service.UserService;
import com.viewnext.bsan.practica03.util.constants.RestApiPaths;
import com.viewnext.bsan.practica03.util.mapper.ControllerLevelUserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(RestApiPaths.BASE_USERS_PATH)
public class UserController {

    private final UserService service;
    private final ControllerLevelUserMapper mapper;

    public UserController(UserService service, ControllerLevelUserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public List<UserReadDto> getRoot() {
        return getAll();
    }

    @GetMapping("/")
    public List<UserReadDto> getAll() {
        return service.getAll().stream().map(mapper::boToReadDto).toList();
    }

    @GetMapping("/{dni}")
    public UserReadDto getByDni(@PathVariable String dni) {
        return mapper.boToReadDto(service.getByDni(dni));
    }

    @PostMapping("")
    public ResponseEntity<Void> postRoot(@RequestBody UserUpsertDto user, UriComponentsBuilder uriComponentsBuilder) {
        return create(user, uriComponentsBuilder);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody UserUpsertDto user, UriComponentsBuilder uriComponentsBuilder) {
        UserBo result = service.create(mapper.dtoToBo(user));

        URI location = uriComponentsBuilder.path(RestApiPaths.BASE_USERS_PATH).path(result.getDni()).build().toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{dni}")
    public ResponseEntity<Void> update(@PathVariable String dni, @RequestBody UserUpsertDto user) {
        service.update(dni, mapper.dtoToBo(user));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> delete(@PathVariable String dni) {
        service.deleteByDni(dni);

        return ResponseEntity.ok().build();
    }

}
