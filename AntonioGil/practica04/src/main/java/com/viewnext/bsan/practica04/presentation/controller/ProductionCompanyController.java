package com.viewnext.bsan.practica04.presentation.controller;

import com.viewnext.bsan.practica04.business.bo.ProductionCompanyBo;
import com.viewnext.bsan.practica04.business.service.ProductionCompanyService;
import com.viewnext.bsan.practica04.presentation.dto.ProductionCompanyReadDto;
import com.viewnext.bsan.practica04.presentation.dto.ProductionCompanyUpsertDto;
import com.viewnext.bsan.practica04.presentation.request.ProductionCompanyFilter;
import com.viewnext.bsan.practica04.presentation.request.QueryOptions;
import com.viewnext.bsan.practica04.util.constants.RestApiPaths;
import com.viewnext.bsan.practica04.util.mapper.ControllerLevelProductionCompanyMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * The {@code ProductionCompanyController} class defines a REST controller for working with production companies.
 *
 * @author Antonio Gil
 */
@RestController
@RequestMapping(RestApiPaths.BASE_PRODUCTION_COMPANIES_PATH)
public class ProductionCompanyController {

    private final ProductionCompanyService service;
    private final ControllerLevelProductionCompanyMapper mapper;

    public ProductionCompanyController(ProductionCompanyService service,
                                       ControllerLevelProductionCompanyMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public List<ProductionCompanyReadDto> getRoot(@ModelAttribute ProductionCompanyFilter filter,
                                                  @ModelAttribute QueryOptions queryOptions) {
        return getAll(filter, queryOptions);
    }

    @GetMapping("/")
    public List<ProductionCompanyReadDto> getAll(@ModelAttribute ProductionCompanyFilter filter,
                                                 @ModelAttribute QueryOptions queryOptions) {
        return service.getAll(filter, queryOptions).stream().map(mapper::boToReadDto).toList();
    }

    @GetMapping("/{id}")
    public ProductionCompanyReadDto getById(@PathVariable long id,
                                            @RequestParam Optional<Boolean> useCustomRepository) {
        return mapper.boToReadDto(service.getById(id, useCustomRepository));
    }

    @PostMapping("")
    public ResponseEntity<Void> postRoot(@RequestBody ProductionCompanyUpsertDto company,
                                         @RequestParam Optional<Boolean> useCustomRepository,
                                         UriComponentsBuilder uriComponentsBuilder) {
        return create(company, useCustomRepository, uriComponentsBuilder);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody ProductionCompanyUpsertDto company,
                                       @RequestParam Optional<Boolean> useCustomRepository,
                                       UriComponentsBuilder uriComponentsBuilder) {
        ProductionCompanyBo bo = mapper.dtoToBo(company);
        ProductionCompanyBo createdBo = service.create(bo, useCustomRepository);

        URI location = uriComponentsBuilder.path(RestApiPaths.BASE_PRODUCTION_COMPANIES_PATH)
                .path("/")
                .path(Long.toString(createdBo.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody ProductionCompanyUpsertDto company,
                                       @RequestParam Optional<Boolean> useCustomRepository) {
        service.update(id, mapper.dtoToBo(company), useCustomRepository);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id, @RequestParam Optional<Boolean> useCustomRepository) {
        service.deleteById(id, useCustomRepository);
        return ResponseEntity.noContent().build();
    }

}
