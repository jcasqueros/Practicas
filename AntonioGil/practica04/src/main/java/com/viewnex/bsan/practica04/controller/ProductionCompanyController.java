package com.viewnex.bsan.practica04.controller;

import com.viewnex.bsan.practica04.bo.ProductionCompanyBo;
import com.viewnex.bsan.practica04.service.ProductionCompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${practica04.rest-api.paths.production-companies}")
public class ProductionCompanyController {

    private final ProductionCompanyService service;

    public ProductionCompanyController(ProductionCompanyService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<ProductionCompanyBo> getRoot() {
        return getAll();
    }

    @GetMapping("/")
    public List<ProductionCompanyBo> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ProductionCompanyBo getById(@PathVariable long id) {
        return service.getById(id);
    }

}
