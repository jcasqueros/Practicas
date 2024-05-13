package com.viewnex.bsan.practica04.controller;

import com.viewnex.bsan.practica04.dto.ProductionCompanyReadDto;
import com.viewnex.bsan.practica04.dto.ProductionCompanyUpsertDto;
import com.viewnex.bsan.practica04.dto.request.QueryOptionsDto;
import com.viewnex.bsan.practica04.dto.request.ProductionCompanyFilterDto;
import com.viewnex.bsan.practica04.service.ProductionCompanyService;
import com.viewnex.bsan.practica04.util.constants.RestApiPaths;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(RestApiPaths.BASE_PRODUCTION_COMPANIES_PATH)
public class ProductionCompanyController {

    private final ProductionCompanyService service;

    public ProductionCompanyController(ProductionCompanyService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<ProductionCompanyReadDto> getRoot(@ModelAttribute ProductionCompanyFilterDto filter,
                                                  @ModelAttribute QueryOptionsDto queryOptions) {
        return getAll(filter, queryOptions);
    }

    @GetMapping("/")
    public List<ProductionCompanyReadDto> getAll(@ModelAttribute ProductionCompanyFilterDto filter,
                                                 @ModelAttribute QueryOptionsDto queryOptions) {
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
