package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.dto.DirectorDTO;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;

import com.santander.peliculacrud.util.exception.GenericException;
import com.santander.peliculacrud.util.mapper.dto.DirectorDTOMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

/**
 * The type Director controller.
 */
@RestController
@RequestMapping("/directors")
public class DirectorController {

    private final DirectorServiceInterface directorService;
    private final CommonOperation commonOperation;
    private final DirectorDTOMapper directorDTOMapper;

    private static final Logger logger = LoggerFactory.getLogger(DirectorController.class);

    /**
     * Instantiates a new Director controller.
     *
     * @param directorService
     *         the director service
     * @param commonOperation
     *         the common operation
     * @param directorDTOMapper
     *         the director dto mapper
     */
    @Autowired
    public DirectorController(DirectorServiceInterface directorService, CommonOperation commonOperation,
            DirectorDTOMapper directorDTOMapper) {
        this.directorService = directorService;
        this.commonOperation = commonOperation;
        this.directorDTOMapper = directorDTOMapper;
    }

    /**
     * Create director response entity.
     *
     * @param director
     *         the director
     * @param bindingResult
     *         the binding result
     * @return the response entity
     * @throws GenericException
     *         the generic exception
     */
    @ApiOperation(value = "Create a new director", notes = "Create a new director with the provided information")
    @ApiResponses({ @ApiResponse(code = 201, message = "Director created successfulnesses"),
            @ApiResponse(code = 400, message = "Invalid request") })
    @PostMapping
    public ResponseEntity<String> createDirector(@Valid @RequestBody DirectorDTO director, BindingResult bindingResult)
            throws GenericException {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Director not created";

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            DirectorBO directorBO = directorDTOMapper.dtoToBo(director);
            if (directorService.createDirector(directorBO) != null) {
                message = "Director created successfully";
                status = HttpStatus.CREATED;

            }

        }
        return new ResponseEntity<>(message, status);

    }

    /**
     * Update director response entity.
     *
     * @param id
     *         the id
     * @param updatedDirector
     *         the updated director
     * @param bindingResult
     *         the binding result
     * @return the response entity
     * @throws GenericException
     *         the generic exception
     */
    @PutMapping("/")
    public ResponseEntity<String> updateDirector(@RequestParam @NotNull Long id,
            @Valid @RequestBody DirectorDTO updatedDirector, BindingResult bindingResult) throws GenericException {
        String message = "Director not update";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            DirectorBO directorBO = directorDTOMapper.dtoToBo(updatedDirector);
            if (directorService.updateDirector(id, directorBO)) {
                message = "Director updated successfully";
                status = HttpStatus.OK;

            }
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Delete director response entity.
     *
     * @param id
     *         the id
     * @return the response entity
     * @throws GenericException
     *         the generic exception
     */
    @DeleteMapping("/")
    public ResponseEntity<String> deleteDirector(@RequestParam @NotNull Long id) throws GenericException {
        String message = "User not delete";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (directorService.deleteDirector(id)) {
            message = "Director delete";
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Gets all directors.
     *
     * @param page
     *         the page
     * @return the all directors
     */
    @GetMapping("/all/")
    public ResponseEntity<List<DirectorDTO>> getAllDirectors(@RequestParam(defaultValue = "0") int page) {
        List<DirectorBO> directorBOS = directorService.getAllDirectors(page);
        return ResponseEntity.ok(directorDTOMapper.bosToDtos(directorBOS));
    }

    /**
     * Gets director by id.
     *
     * @param id
     *         the id
     * @return the director by id
     */
    @GetMapping("/")
    public ResponseEntity<DirectorDTO> getDirectorById(@RequestParam @NotNull Long id) {
        DirectorBO directorBO = directorService.getDirectorById(id);
        if (directorBO == null) {
            logger.error("Director not found");
            return ResponseEntity.notFound().build();
        }
        DirectorDTO directorDTO = directorDTOMapper.boToDto(directorBO);
        return ResponseEntity.ok(directorDTO);
    }

    /**
     * Gets directors by age.
     *
     * @param age
     *         the age
     * @param page
     *         the page
     * @return the directors by age
     */
    @GetMapping("/by-age/")
    public ResponseEntity<List<DirectorDTO>> getDirectorsByAge(@RequestParam int age,
            @RequestParam(defaultValue = "0") int page) {
        List<DirectorBO> directorBOs = directorService.getDirectorByAge(age, page);
        if (directorBOs.isEmpty()) {
            logger.error("No directors found with age {}", age);
            return ResponseEntity.notFound().build();
        }
        List<DirectorDTO> directorDTOS = directorDTOMapper.bosToDtos(directorBOs);
        return ResponseEntity.ok(directorDTOS);
    }

    /**
     * Gets directors by name.
     *
     * @param name
     *         the name
     * @param page
     *         the page
     * @return the directors by name
     */
    @GetMapping("/by-name/")
    public ResponseEntity<List<DirectorDTO>> getDirectorsByName(@RequestParam String name,
            @RequestParam(defaultValue = "0") int page) {
        List<DirectorBO> directorBOs = directorService.getDirectorByName(name, page);
        if (directorBOs.isEmpty()) {
            logger.error("No directors found with name {}", name);
            return ResponseEntity.notFound().build();
        }
        List<DirectorDTO> directorDTOS = directorDTOMapper.bosToDtos(directorBOs);
        return ResponseEntity.ok(directorDTOS);
    }

    /**
     * Gets directors by nation.
     *
     * @param nation
     *         the nation
     * @param page
     *         the page
     * @return the directors by nation
     */
    @GetMapping("/by-nation/")
    public ResponseEntity<List<DirectorDTO>> getDirectorsByNation(@RequestParam String nation,
            @RequestParam(defaultValue = "0") int page) {
        List<DirectorBO> directorBOs = directorService.getDirectorByNation(nation, page);
        if (directorBOs.isEmpty()) {
            logger.error("No directors found with nation {}", nation);
            return ResponseEntity.notFound().build();
        }
        List<DirectorDTO> directorDTOS = directorDTOMapper.bosToDtos(directorBOs);
        return ResponseEntity.ok(directorDTOS);
    }
    @GetMapping("/by-all-filter/")
    public ResponseEntity<List<DirectorDTO>> getDirectorsByAllFilter(@RequestParam List<String> name,@RequestParam List<Integer> age,@RequestParam List<String> nation,
            @RequestParam(defaultValue = "0") int page) {
        List<DirectorBO> directorBOs = directorService.getDirectorByAllFilter(name, age, nation, page);
        if (directorBOs.isEmpty()) {
            logger.error("No directors found with nation {}, name {}, age {}", nation, name , age);
            return ResponseEntity.notFound().build();
        }
        List<DirectorDTO> directorDTOS = directorDTOMapper.bosToDtos(directorBOs);
        return ResponseEntity.ok(directorDTOS);
    }
}

