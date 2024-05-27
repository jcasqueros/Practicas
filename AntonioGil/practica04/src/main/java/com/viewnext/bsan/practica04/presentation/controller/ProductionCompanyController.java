package com.viewnext.bsan.practica04.presentation.controller;

import com.viewnext.bsan.practica04.business.service.ProductionCompanyService;
import com.viewnext.bsan.practica04.presentation.dto.ProductionCompanyReadDto;
import com.viewnext.bsan.practica04.presentation.dto.ProductionCompanyUpsertDto;
import com.viewnext.bsan.practica04.presentation.request.ProductionCompanyFilter;
import com.viewnext.bsan.practica04.presentation.request.QueryOptions;
import com.viewnext.bsan.practica04.util.constants.RestApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    public ProductionCompanyController(ProductionCompanyService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<ProductionCompanyReadDto> getRoot(@ModelAttribute ProductionCompanyFilter filter,
                                                  @ModelAttribute QueryOptions queryOptions) {
        return getAll(filter, queryOptions);
    }

    @GetMapping("/")
    public List<ProductionCompanyReadDto> getAll(@ModelAttribute ProductionCompanyFilter filter,
                                                 @ModelAttribute QueryOptions queryOptions) {
        // TODO: Implement read query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @GetMapping("/{id}")
    public ProductionCompanyReadDto getById(@PathVariable long id,
                                            @RequestParam Optional<Boolean> useCustomRepository) {
        // TODO: Implement read query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @PostMapping("")
    public ResponseEntity<Void> postRoot(@RequestBody ProductionCompanyUpsertDto company,
                                         @RequestParam Optional<Boolean> useCustomRepository) {
        return create(company, useCustomRepository);
    }

    @PostMapping("/")
    public ResponseEntity<Void> create(@RequestBody ProductionCompanyUpsertDto company,
                                       @RequestParam Optional<Boolean> useCustomRepository) {
        // TODO: Implement create query
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody ProductionCompanyUpsertDto company,
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
