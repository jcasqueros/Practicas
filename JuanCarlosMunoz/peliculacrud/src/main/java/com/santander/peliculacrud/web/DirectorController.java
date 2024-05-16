package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.dto.DirectorDTO;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;

import com.santander.peliculacrud.util.mapper.DirectorDTOMapper;
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

    @Autowired
    private DirectorServiceInterface directorService;
    @Autowired
    private CommonOperation commonOperation;
    @Autowired
    private DirectorDTOMapper directorDTOMapper;

    private static final Logger logger = LoggerFactory.getLogger(DirectorController.class);

    /**
     * Create director response entity.
     *
     * @param director
     *         the director
     * @param bindingResult
     *         the binding result
     * @return the response entity
     */
    @ApiOperation(value = "Create a new director", notes = "Create a new director with the provided information")
    @ApiResponses({ @ApiResponse(code = 201, message = "Director created successfulnesses"),
            @ApiResponse(code = 400, message = "Invalid request") })
    @PostMapping
    public ResponseEntity<String> createDirector(@Valid @RequestBody DirectorDTO director, BindingResult bindingResult) {

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
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDirector(@PathVariable @NotNull Long id, @Valid @RequestBody DirectorDTO updatedDirector,
            BindingResult bindingResult) {
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
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDirector(@PathVariable @NotNull Long id) {
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
     * @return the all directors
     */
    @GetMapping()
    public List<DirectorDTO> getAllDirectors() {
        List<DirectorBO> directorBOS = directorService.getAllDirectors();
        return directorDTOMapper.bosToDtos(directorBOS);
    }

    /**
     * Gets director by id.
     *
     * @param id
     *         the id
     * @return the director by id
     */
    @GetMapping("/{id}")
    public DirectorDTO getDirectorById(@PathVariable @NotNull Long id) {
        DirectorBO directorBO = directorService.getDirectorById(id);
        DirectorDTO directorDTO = directorDTOMapper.boToDto(directorBO);

        if (directorDTO == null) {
            logger.error("Director not found");
        }
        return directorDTO;

    }
}

