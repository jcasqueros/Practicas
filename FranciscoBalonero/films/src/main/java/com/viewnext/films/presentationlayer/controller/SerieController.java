package com.viewnext.films.presentationlayer.controller;

import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.SerieService;
import com.viewnext.films.presentationlayer.dto.SerieInDTO;
import com.viewnext.films.presentationlayer.dto.SerieOutDTO;
import com.viewnext.films.presentationlayer.dto.SerieUpdateDTO;
import com.viewnext.films.util.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing series.
 *
 * <p>This class defines RESTful endpoints for performing CRUD operations on series. It handles
 * requests related to retrieving all series, retrieving a serie by id, updating serie, deleting series by id, and save
 * a serie.</p>
 *
 * @author Franciosco Balonero Olivera
 * @see SerieService
 * @see Converter
 */
@RestController
@RequestMapping("api/v1/Serie")
@RequiredArgsConstructor
@Validated
public class SerieController {

    private final SerieService serieService;

    private final Converter converter;

    @Operation(summary = "Get all Series")
    @GetMapping("/getAllSeries")
    public ResponseEntity<List<SerieOutDTO>> getAllSeries(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select)
            throws ServiceException {
        if (select) {
            return new ResponseEntity<>(serieService.criteriaGetAll().stream().map(converter::serieBOToOutDTO).toList(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(serieService.jpaGetAll().stream().map(converter::serieBOToOutDTO).toList(),
                    HttpStatus.OK);
        }
    }

    @Operation(summary = "Get Serie by id")
    @GetMapping("/getSerie")
    public ResponseEntity<SerieOutDTO> getSerieById(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestParam @Parameter(description = "Id of the Serie") long id) throws ServiceException {
        if (select) {
            return new ResponseEntity<>(converter.serieBOToOutDTO(serieService.criteriaGetById(id)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(converter.serieBOToOutDTO(serieService.jpaGetById(id)), HttpStatus.OK);
        }
    }

    @Operation(summary = "Save a new Serie")
    @PostMapping("/save")
    public ResponseEntity<SerieOutDTO> saveSerie(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestBody @Valid SerieInDTO serieInDTO) throws ServiceException {
        if (select) {
            return new ResponseEntity<>(
                    converter.serieBOToOutDTO(serieService.criteriaCreate(converter.serieInDTOToBO(serieInDTO))),
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    converter.serieBOToOutDTO(serieService.jpaCreate(converter.serieInDTOToBO(serieInDTO))),
                    HttpStatus.CREATED);
        }
    }

    @Operation(summary = "Delete a Serie")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteSerie(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestParam @Parameter(description = "Id of the Serie") long id) throws ServiceException {
        if (select) {
            serieService.criteriaDeleteById(id);
        } else {
            serieService.jpaDeleteById(id);
        }
        return new ResponseEntity<>("The serie has been successfully deleted ", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a Serie")
    @PutMapping("/update")
    public ResponseEntity<SerieOutDTO> updateSerie(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestBody @Valid SerieUpdateDTO serieUpdateDTO) throws ServiceException {
        if (select) {
            return new ResponseEntity<>(converter.serieBOToOutDTO(
                    serieService.criteriaUpdate(converter.serieUpdateDTOToBO(serieUpdateDTO))), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    converter.serieBOToOutDTO(serieService.jpaUpdate(converter.serieUpdateDTOToBO(serieUpdateDTO))),
                    HttpStatus.CREATED);
        }

    }
}