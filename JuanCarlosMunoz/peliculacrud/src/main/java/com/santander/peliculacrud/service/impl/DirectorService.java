package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.DirectorRepository;
import com.santander.peliculacrud.model.input.Director;

import com.santander.peliculacrud.model.output.DirectorModelService;
import com.santander.peliculacrud.service.DirectorServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.TransformObjects;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * The type Director service.
 */
@Service
public class DirectorService implements DirectorServiceInterface{

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private TransformObjects transformObjects;

    @Autowired
    private Validator validator;

    private static final Logger logger = LoggerFactory.getLogger(DirectorService.class);

    @Autowired
    private CommonOperation commonOperation;

    /**
     * Create director.
     *
     * @param directorModelService
     *         the director out
     */


    public Director createDirector(@Valid DirectorModelService directorModelService) {
        Director directorReturn = Director.builder().build();

        BindingResult result = new BeanPropertyBindingResult(directorModelService, "directorModelService");
        validator.validate(directorModelService, result);

        if (result.hasErrors()) {
            commonOperation.showErrorModel(logger, result);
            throw new RuntimeException("Invalid director data: " + result.getAllErrors());
        } else {
            Director director = transformObjects.directorOutToDirector(directorModelService);
            try {
                directorReturn = directorRepository.save(director);
            } catch (Exception e) {
                logger.error("Failed to create director: {}", e.getMessage());
                throw new RuntimeException("Failed to create director: ", e);
            }
        }
        return directorReturn;
    }


    /**
     * Update director boolean.
     *
     * @param id
     *         the id
     * @param directorModelService
     *         the director out
     * @return the boolean
     */
    public boolean updateDirector(Long id, @Valid DirectorModelService directorModelService) {
        boolean update = false;
        if (directorRepository.existsById(id)) {

            BindingResult result = new BeanPropertyBindingResult(directorModelService, "directorModelService");
            validator.validate(directorModelService, result);

            if (result.hasErrors()) {
                commonOperation.showErrorModel(logger, result);
                throw new RuntimeException("Invalid director data: " + result.getAllErrors());
            } else {

                try {
                    Director director = transformObjects.directorOutToDirector(directorModelService);
                    director.setId(id);
                    directorRepository.save(director);
                    update = directorRepository.existsById(id);

                } catch (Exception e) {

                    logger.error("Failed to update director: {}", e.getMessage());
                    throw new RuntimeException("Failed to update director: ", e);

                }
            }

        } else {
            directorNotfound();
            throw new RuntimeException("Director not found");
        }
        return update;

    }

    /**
     * Delete director boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    public boolean deleteDirector(Long id) {
        boolean delete = false;
        if (directorRepository.existsById(id)) {
            try {
                directorRepository.deleteById(id);

                delete = !directorRepository.existsById(id);

            } catch (Exception e) {
                logger.error("Failed to delete user: {}", e.getMessage());
                throw new RuntimeException("Failed to delete user: {}", e);
            }

        } else {
            directorNotfound();
        }

        return delete;
    }

    /**
     * Gets all directors.
     *
     * @return the all directors
     */
    public List<DirectorModelService> getAllDirectors() {

        List<Director> directors = directorRepository.findAll();

        return transformObjects.directorsToDirectorsOut(directors);
    }

    /**
     * Gets director by id.
     *
     * @param id
     *         the id
     * @return the director by id
     */
    public DirectorModelService getDirectorById(Long id) {

        Director director = directorRepository.findById(id).orElse(null);

        DirectorModelService directorModelService = null;

        if (director != null) {
            directorModelService = transformObjects.directorToDirectorOut(director);
        }

        return directorModelService;
    }

    /**
     * Gets last director.
     *
     * @return the last director
     */
    public Director getLastDirector() {
        List<Director> directors = directorRepository.findLastDirector();
        return directors.get(0);
    }

    /**
     * Directors list size int.
     *
     * @return the int
     */
    public int getListSize() {
        return directorRepository.findAll().size();
    }

    private void directorNotfound() {
        logger.error("Director not found");
        throw new RuntimeException("Director not found");

    }

}
