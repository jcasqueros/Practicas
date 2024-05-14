/*
package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

*/
/**
 * The type Director controller.
 *//*

@RestController
@RequestMapping("/directors")
public class DirectorController {

    @Autowired
    private DirectorServiceInterface directorService;
    @Autowired
    private CommonOperation commonOperation;

    private static final Logger logger = LoggerFactory.getLogger(DirectorController.class);

    */
/**
     * Create director string.
     *
     * @param director
     *         the director
     * @param bindingResult
     *         the binding result
     * @return the string
     *//*

    @PostMapping
    public ResponseEntity<String> createDirector(@Valid @RequestBody DirectorBO director,
            BindingResult bindingResult) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Director not created";

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            directorService.createDirector(director);
            message = "Director created successfully";
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(message, status);

    }

    */
/**
     * Update director string.
     *
     * @param id
     *         the id
     * @param updatedDirector
     *         the updated director
     * @param bindingResult
     *         the binding result
     * @return the string
     *//*

    @PutMapping("/{id}")
    public ResponseEntity<String> updateDirector(@PathVariable @NotNull Long id,
            @Valid @RequestBody DirectorBO updatedDirector, BindingResult bindingResult) {
        String message = "Director not update";
        HttpStatus status = HttpStatus.OK;

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);
            status = HttpStatus.BAD_REQUEST;

        } else if (directorService.updateDirector(id, updatedDirector)) {
            message = "Director updated successfully";
        }

        return new ResponseEntity<>(message, status);
    }

    */
/**
     * Delete director string.
     *
     * @param id
     *         the id
     * @return the string
     *//*

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDirector(@PathVariable @NotNull Long id) {
        String message = "Director not delete";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (directorService.deleteDirector(id)) {
            message = "Director delete";
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(message, status);
    }

    */
/**
     * Gets all directors.
     *
     * @return the all directors
     *//*

    @GetMapping()
    public List<DirectorBO> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    */
/**
     * Gets director by id.
     *
     * @param id
     *         the id
     * @return the director by id
     *//*

    @GetMapping("/{id}")
    public DirectorBO getDirectorById(@PathVariable @NotNull Long id) {

        DirectorBO directorBO = this.directorService.getDirectorById(id);

        if (directorBO == null) {
            logger.error("Director not found");
        }
        return directorBO;

    }
}
*/
