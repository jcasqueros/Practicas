package com.santander.peliculacrud.service;


import com.santander.peliculacrud.model.bo.DirectorBO;
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
     * @param directorBO
     *         the director out
     * @return the director
     */
    DirectorBO createDirector(DirectorBO directorBO);

    /**
     * Gets all directors.
     *
     * @return the all directors
     */
    List<DirectorBO> getAllDirectors();

    /**
     * Gets director by id.
     *
     * @param id
     *         the id
     * @return the director by id
     */
    DirectorBO getDirectorById(long id);

    /**
     * Update director boolean.
     *
     * @param id
     *         the id
     * @param directorBO
     *         the director outº
     * @return the boolean
     */
    boolean updateDirector(long id, DirectorBO directorBO);

    /**
     * Delete director boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     */
    boolean deleteDirector(long id);


}
