package com.santander.peliculacrud.service;

import com.santander.peliculacrud.model.input.Director;
import com.santander.peliculacrud.model.output.DirectorModelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The interface Director service interface.
 */
@Service
public interface DirectorServiceInterface {
    /**
     * Create director.
     *
     * @param directorModelService
     *         the director out
     * @return the director
     */
    Director createDirector(DirectorModelService directorModelService);

    /**
     * Gets all directors.
     *
     * @return the all directors
     */
    List<DirectorModelService> getAllDirectors();

    /**
     * Gets director by id.
     *
     * @param id
     *         the id
     * @return the director by id
     */
    DirectorModelService getDirectorById(Long id);

    /**
     * Update director boolean.
     *
     * @param id
     *         the id
     * @param directorModelService
     *         the director out
     * @return the boolean
     */
    boolean updateDirector(Long id, DirectorModelService directorModelService);

    /**
     * Delete director boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    boolean deleteDirector(Long id);

    /**
     * Gets las director.
     *
     * @return the las director
     */
    Director getLastDirector();

    /**
     * Gets list size.
     *
     * @return the list size
     */
    int getListSize();
}
