package com.santander.peliculacrud.web;

import com.santander.peliculacrud.model.output.FilmModelController;
import com.santander.peliculacrud.model.output.FilmModelService;
import com.santander.peliculacrud.service.FilmServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type FilmModelService controller.
 */
@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmServiceInterface filmService;

    @Autowired
    private CommonOperation commonOperation;

    private static final Logger logger = LoggerFactory.getLogger(FilmController.class);

    /**
     * Create film out response entity.
     *
     * @param filmModelController
     *         the film out
     * @param bindingResult
     *         the binding result
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<String> createFilmOut(@Valid @RequestBody FilmModelController filmModelController, BindingResult bindingResult) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Film not created";
        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            if (filmService.createFilm(filmModelController)) {
                status = HttpStatus.CREATED;
                message = "Film created successfully";

            }
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Update film out response entity.
     *
     * @param id
     *         the id
     * @param updatedFilmModelController
     *         the updated film out
     * @param bindingResult
     *         the binding result
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateFilmOut(@PathVariable Long id, @Valid @RequestBody FilmModelController updatedFilmModelController,
            BindingResult bindingResult) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Film not updated";
        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            if (filmService.updateFilm(id, updatedFilmModelController)) {
                status = HttpStatus.OK;
                message = "Film updated successfully";

            }
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Gets all film outs.
     *
     * @return the all film outs
     */
    @GetMapping()
    public List<FilmModelService> getAllFilmOuts() {
        return filmService.getAllFilm();
    }

    /**
     * Gets film out by id.
     *
     * @param id
     *         the id
     * @return the film out by id
     */
    @GetMapping("/{id}")
    public FilmModelService getFilmOutById(@PathVariable Long id) {
        return filmService.filmOut(id);
    }

    /**
     * Delete film out response entity.
     *
     * @param id
     *         the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFilmOut(@PathVariable Long id) {
        String message = "Film not deleted";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (id != null) {

            if (filmService.deleteFilm(id)) {
                message = "Film deleted";
                status = HttpStatus.NO_CONTENT;
            }

        } else {
            logger.error("Id is null");

        }

        return new ResponseEntity<>(message, status);
    }

}
