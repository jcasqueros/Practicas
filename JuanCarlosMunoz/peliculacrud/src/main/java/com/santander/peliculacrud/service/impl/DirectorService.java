package com.santander.peliculacrud.service.impl;

import com.santander.peliculacrud.model.api.DirectorRepository;
import com.santander.peliculacrud.model.bo.DirectorBO;

import com.santander.peliculacrud.model.dto.UserDTO;
import com.santander.peliculacrud.model.entity.Director;

import com.santander.peliculacrud.service.DirectorServiceInterface;
import com.santander.peliculacrud.service.EndpointServiceInterface;
import com.santander.peliculacrud.util.exception.GenericException;
import com.santander.peliculacrud.util.mapper.bo.DirectorBOMapper;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * The type Director service.
 */
@Service
public class DirectorService implements DirectorServiceInterface {

    private final DirectorRepository directorRepository;
    private final DirectorBOMapper directorBOMapper;
    private final EndpointServiceInterface endpointService;

    private static final Logger logger = LoggerFactory.getLogger(DirectorService.class);

    /**
     * Instantiates a new Director service.
     *
     * @param directorBOMapper
     *         the director bo mapper
     * @param directorRepository
     *         the director repository
     * @param endpointService
     *         the endpoint service
     */
    @Autowired
    public DirectorService(DirectorBOMapper directorBOMapper, DirectorRepository directorRepository,
            EndpointServiceInterface endpointService) {
        this.directorRepository = directorRepository;
        this.directorBOMapper = directorBOMapper;

        this.endpointService = endpointService;
    }

    @Override
    public DirectorBO createDirector(DirectorBO directorBO) throws GenericException {
        Director director;

        director = directorBOMapper.boToEntity(directorBO);

        try {
            List<UserDTO> users = endpointService.getUserByNameAndAge(directorBO.getName(), directorBO.getAge());

            if (!users.isEmpty()) {
                director = directorRepository.save(director);
                directorBO = directorBOMapper.entityToBo(director);
            } else {
                throw new GenericException("No existe en la tabla de usuarios");

            }

        } catch (Exception e) {
            throw new GenericException("Failed to create director: ", e);
        }

        return directorBO;

    }

    @Override
    public List<DirectorBO> getAllDirectors(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Director> directorsPage = directorRepository.findAll(pageable);
        List<DirectorBO> directors = directorBOMapper.listEntitytoListBo(directorsPage);

        return directors.stream().sorted(Comparator.comparing(DirectorBO::getName)).toList();
    }

    @Override
    public DirectorBO getDirectorById(long id) {
        Director director = directorRepository.findById(id).orElse(null);
        DirectorBO directorBO = null;
        if (director != null) {
            directorBO = directorBOMapper.entityToBo(director);
        }

        return directorBO;
    }

    @Override
    public boolean updateDirector(long id, DirectorBO directorBO) throws GenericException {
        boolean update = false;
        if (directorRepository.existsById(id)) {

            try {
                Director director = directorBOMapper.boToEntity(directorBO);
                director.setId(id);
                directorRepository.save(director);
                update = directorRepository.existsById(id);

            } catch (Exception e) {

                throw new GenericException("Failed to update director: ", e);

            }

        } else {
            directorNotfound();
        }
        return update;
    }

    @Override
    public boolean deleteDirector(long id) throws GenericException {

        boolean delete = false;
        if (directorRepository.existsById(id)) {
            directorRepository.deleteById(id);

            delete = true;

        } else {
            directorNotfound();
        }

        return delete;
    }

    @Override
    public List<DirectorBO> getDirectorByName(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Director> directorsPage = directorRepository.findAllByName(name, pageable);
        List<DirectorBO> directors = directorBOMapper.listEntitytoListBo(directorsPage);

        return directors.stream().sorted(Comparator.comparingInt(DirectorBO::getAge)).toList();
    }

    @Override
    public List<DirectorBO> getDirectorByAge(int age, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Director> directorsPage = directorRepository.findAllByAgeEquals(age, pageable);
        List<DirectorBO> directors = directorBOMapper.listEntitytoListBo(directorsPage);

        return directors.stream().sorted(Comparator.comparing(DirectorBO::getName)).toList();
    }

    @Override
    public List<DirectorBO> getDirectorByNation(String nation, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Director> directorsPage = directorRepository.findAllByNationEquals(nation, pageable);
        List<DirectorBO> directors = directorBOMapper.listEntitytoListBo(directorsPage);

        return directors.stream().sorted(Comparator.comparing(DirectorBO::getName)).toList();
    }

    private void directorNotfound() throws GenericException {
        logger.error("Director not found");
        throw new GenericException("Director not found");

    }

}
