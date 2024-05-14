package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.DirectorRepository;
import com.santander.peliculacrud.model.bo.DirectorBO;
import com.santander.peliculacrud.model.entity.Director;

import com.santander.peliculacrud.service.DirectorServiceInterface;
import com.santander.peliculacrud.util.CommonOperation;
import com.santander.peliculacrud.util.mapper.DirectorBOMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import org.springframework.validation.Validator;

import java.util.List;

/**
 * The type Director service.
 */
@Service
public class DirectorService implements DirectorServiceInterface {

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private DirectorBOMapper directorBOMapper;

    @Autowired
    private Validator validator;

    private static final Logger logger = LoggerFactory.getLogger(DirectorService.class);

    @Autowired
    private CommonOperation commonOperation;

    @Override
    public DirectorBO createDirector(DirectorBO directorBO) {
        Director director = Director.builder().build();

        director = directorBOMapper.boToEntity(directorBO);

        try {
            director = directorRepository.save(director);
            directorBO = directorBOMapper.entityToBo(director);

        } catch (DataAccessException e) {
            logger.error("Failed to create director", e);
            throw new RuntimeException("Failed to create director: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Failed to create director: {}", e.getMessage());
            throw new RuntimeException("Failed to create director: ", e);
        }

        return directorBO;

    }

    @Override
    public List<DirectorBO> getAllDirectors() {
        List<Director> directors = directorRepository.findAll();
        return directorBOMapper.listEntitytoListBo(directors);
    }

    @Override
    public DirectorBO getDirectorById(long id) {
        Director director = directorRepository.findById(id).orElse(null);
        DirectorBO directorBO = DirectorBO.builder().build();
        if (director != null) {
            directorBO = directorBOMapper.entityToBo(director);
        }

        return directorBO;
    }

    @Override
    public boolean updateDirector(long id, DirectorBO directorBO) {
        boolean update = false;
        if (directorRepository.existsById(id)) {

            try {
                Director director = directorBOMapper.boToEntity(directorBO);
                director.setId(id);
                directorRepository.save(director);
                update = directorRepository.existsById(id);

            } catch (Exception e) {

                logger.error("Failed to update director: {}", e.getMessage());
                throw new RuntimeException("Failed to update director: ", e);

            }

        } else {
            directorNotfound();
            throw new RuntimeException("Director not found");
        }
        return update;
    }

    @Override
    public boolean deleteDirector(long id) {

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

    private void directorNotfound() {
        logger.error("Director not found");
        throw new RuntimeException("Director not found");

    }

}
