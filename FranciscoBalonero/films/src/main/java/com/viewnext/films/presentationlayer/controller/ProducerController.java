package com.viewnext.films.presentationlayer.controller;

import com.viewnext.films.businesslayer.exception.ServiceException;
import com.viewnext.films.businesslayer.service.ProducerService;
import com.viewnext.films.presentationlayer.dto.ProducerInDTO;
import com.viewnext.films.presentationlayer.dto.ProducerOutDTO;
import com.viewnext.films.presentationlayer.dto.ProducerUpdateDTO;
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
 * Controller class for managing producers.
 *
 * <p>This class defines RESTful endpoints for performing CRUD operations on producers. It handles
 * requests related to retrieving all producers, retrieving a producer by id, updating producer, deleting producers by
 * id, and save a producer.</p>
 *
 * @author Franciosco Balonero Olivera
 * @see ProducerService
 * @see Converter
 */
@RestController
@RequestMapping("api/v1/Producer")
@RequiredArgsConstructor
@Validated
public class ProducerController {

    private final ProducerService producerService;

    private final Converter converter;

    @Operation(summary = "Get all Producers")
    @GetMapping("/getAllProducers")
    public ResponseEntity<List<ProducerOutDTO>> getAllProducers(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select)
            throws ServiceException {
        if (select) {
            return new ResponseEntity<>(
                    producerService.criteriaGetAll().stream().map(converter::producerBOToOutDTO).toList(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    producerService.jpaGetAll().stream().map(converter::producerBOToOutDTO).toList(), HttpStatus.OK);
        }
    }

    @Operation(summary = "Get Producer by id")
    @GetMapping("/getProducer")
    public ResponseEntity<ProducerOutDTO> getProducerById(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestParam @Parameter(description = "Id of the Producer") long id) throws ServiceException {
        if (select) {
            return new ResponseEntity<>(converter.producerBOToOutDTO(producerService.criteriaGetById(id)),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>(converter.producerBOToOutDTO(producerService.jpaGetById(id)), HttpStatus.OK);
        }
    }

    @Operation(summary = "Save a new Producer")
    @PostMapping("/save")
    public ResponseEntity<ProducerOutDTO> saveProducer(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestBody @Valid ProducerInDTO producerInDTO) throws ServiceException {
        if (select) {
            return new ResponseEntity<>(converter.producerBOToOutDTO(
                    producerService.criteriaCreate(converter.producerInDTOToBO(producerInDTO))), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(
                    converter.producerBOToOutDTO(producerService.jpaCreate(converter.producerInDTOToBO(producerInDTO))),
                    HttpStatus.CREATED);
        }
    }

    @Operation(summary = "Delete a Producer")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProducer(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestParam @Parameter(description = "Id of the Producer") long id) throws ServiceException {
        if (select) {
            producerService.criteriaDeleteById(id);
        } else {
            producerService.jpaDeleteById(id);
        }
        return new ResponseEntity<>("The producer has been successfully deleted ", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a Producer")
    @PutMapping("/update")
    public ResponseEntity<ProducerOutDTO> updateProducer(
            @RequestParam @Parameter(description = "True if Criteria, False JPA") boolean select,
            @RequestBody @Valid ProducerUpdateDTO producerUpdateDTO) throws ServiceException {
        if (select) {
            return new ResponseEntity<>(converter.producerBOToOutDTO(
                    producerService.criteriaUpdate(converter.producerUpdateDTOToBO(producerUpdateDTO))),
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(converter.producerBOToOutDTO(
                    producerService.jpaUpdate(converter.producerUpdateDTOToBO(producerUpdateDTO))), HttpStatus.CREATED);
        }

    }
}