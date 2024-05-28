package com.santander.peliculacrud.service;

import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.util.exception.GenericException;
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
     * @throws GenericException
     *         the generic exception
     */
    DirectorBO createDirector(DirectorBO directorBO) throws GenericException;

    /**
     * Gets all directors.
     *
     * @param page
     *         the page
     * @return the all directors
     */
    List<DirectorBO> getAllDirectors(int page);

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
     * @throws GenericException
     *         the generic exception
     */
    boolean updateDirector(long id, DirectorBO directorBO) throws GenericException;

    /**
     * Delete director boolean.
     *
     * @param id
     *         the id
     * @return the boolean
     * @throws GenericException
     *         the generic exception
     */
    boolean deleteDirector(long id) throws GenericException;

    /**
     * Gets director by age.
     *
     * @param age
     *         the age
     * @param page
     *         the page
     * @return the director by age
     */
    List<DirectorBO> getDirectorByAge(int age, int page);

    /**
     * Gets director by name.
     *
     * @param name
     *         the name
     * @param page
     *         the page
     * @return the director by name
     */
    List<DirectorBO> getDirectorByName(String name, int page);

    /**
     * Gets director by nation.
     *
     * @param nation
     *         the nation
     * @param page
     *         the page
     * @return the director by nation
     */
    List<DirectorBO> getDirectorByNation(String nation, int page);

}
