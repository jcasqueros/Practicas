package com.pracs.films.bussiness.services.impl;

import com.pracs.films.bussiness.bo.DirectorBO;
import com.pracs.films.bussiness.converters.BoToModelConverter;
import com.pracs.films.bussiness.converters.ModelToBoConverter;
import com.pracs.films.bussiness.services.DirectorService;
import com.pracs.films.configuration.ConstantMessages;
import com.pracs.films.exceptions.DuplicatedIdException;
import com.pracs.films.exceptions.EmptyException;
import com.pracs.films.exceptions.EntityNotFoundException;
import com.pracs.films.exceptions.ServiceException;
import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.repositories.criteria.impl.DirectorRepositoryImpl;
import com.pracs.films.persistence.repositories.jpa.DirectorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the interface {@link DirectorService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final ModelToBoConverter modelToBoConverter;

    private final BoToModelConverter boToModelConverter;

    private final DirectorRepository directorRepository;

    private final DirectorRepositoryImpl directorRepositoryCriteria;

    @Override
    public DirectorBO save(DirectorBO directorBO) throws ServiceException {
        try {
            //Comprobar si existe ya un director registrado con el mismo id.
            if (directorRepository.existsById(directorBO.getId())) {
                throw new DuplicatedIdException("Existing person");
            }

            // Conversión de model a bo del resultado de crear un director.
            return modelToBoConverter.directorModelToBo(
                    directorRepository.save(boToModelConverter.directorBoToModel(directorBO)));
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public DirectorBO update(DirectorBO directorBO) throws ServiceException {
        try {
            // Búsqueda de un director con el id introducido para comprobar que existe
            DirectorBO savedDirectorBO = modelToBoConverter.directorModelToBo(
                    directorRepository.findById(directorBO.getId())
                            .orElseThrow(() -> new EntityNotFoundException(ConstantMessages.ERRORPERSON)));

            //Actualización con los campos introducidos
            savedDirectorBO.setName(directorBO.getName());
            savedDirectorBO.setAge(directorBO.getAge());
            savedDirectorBO.setNationality(directorBO.getNationality());

            // Conversion de model a bo del resultado de guardar un director
            return modelToBoConverter.directorModelToBo(
                    directorRepository.save(boToModelConverter.directorBoToModel(savedDirectorBO)));
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public DirectorBO findById(long id) throws ServiceException {
        try {
            //Comprobar si existe ya un director registrado con el mismo id.
            return modelToBoConverter.directorModelToBo(directorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(ConstantMessages.ERRORPERSON)));
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<DirectorBO> findByNameAndAge(String name, int age) throws ServiceException {
        try {
            List<DirectorBO> directorBOList = directorRepository.findByNameAndAge(name, age).stream()
                    .map(modelToBoConverter::directorModelToBo).toList();

            if (directorBOList.isEmpty()) {
                throw new EmptyException(ConstantMessages.NODIRECTORS);
            }

            return directorBOList;
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<DirectorBO> findAll(Pageable pageable) throws ServiceException {
        try {
            //Búsqueda de los todos lo directors, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            Page<Director> directorPage = directorRepository.findAll(pageable);

            if (directorPage.isEmpty()) {
                throw new EmptyException(ConstantMessages.NODIRECTORS);
            }

            List<DirectorBO> directorBOList = directorPage.stream().map(modelToBoConverter::directorModelToBo).toList();

            return new PageImpl<>(directorBOList, directorPage.getPageable(), directorPage.getTotalPages());
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteById(long id) throws ServiceException {
        try {
            //Comprobar si el director no existe
            if (!directorRepository.existsById(id)) {
                log.error("EntityNotFoundException");
                throw new EntityNotFoundException(ConstantMessages.ERRORPERSON);
            }

            directorRepository.deleteById(id);
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public DirectorBO saveCriteria(DirectorBO directorBO) throws ServiceException {
        try {
            //Comprobar si existe ya un director registrado con el mismo id.
            if (!directorRepositoryCriteria.findDirectorById(directorBO.getId()).isEmpty()) {
                throw new DuplicatedIdException("Existing person");
            }

            // Conversión de model a bo del resultado de crear un director.
            return modelToBoConverter.directorModelToBo(
                    directorRepositoryCriteria.saveDirector(boToModelConverter.directorBoToModel(directorBO)));
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public DirectorBO updateCriteria(DirectorBO directorBO) throws ServiceException {
        try {
            // Búsqueda de un director con el id introducido para comprobar que existe
            DirectorBO savedDirectorBO = modelToBoConverter.directorModelToBo(
                    directorRepositoryCriteria.findDirectorById(directorBO.getId())
                            .orElseThrow(() -> new EntityNotFoundException(ConstantMessages.ERRORPERSON)));

            //Actualización con los campos introducidos
            savedDirectorBO.setName(directorBO.getName());
            savedDirectorBO.setAge(directorBO.getAge());
            savedDirectorBO.setNationality(directorBO.getNationality());

            // Conversion de model a bo del resultado de guardar un director
            return modelToBoConverter.directorModelToBo(
                    directorRepositoryCriteria.updateDirector(boToModelConverter.directorBoToModel(savedDirectorBO)));
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public DirectorBO findByIdCriteria(long id) throws ServiceException {
        try {
            // Conversión de model a bo del resultado de buscar un director por id.
            return modelToBoConverter.directorModelToBo(directorRepositoryCriteria.findDirectorById(id)
                    .orElseThrow(() -> new EntityNotFoundException(ConstantMessages.ERRORPERSON)));
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<DirectorBO> findByNameAndAgeCriteria(String name, int age) throws ServiceException {
        try {
            List<DirectorBO> directorBOList = directorRepositoryCriteria.findByNameAndAge(name, age).stream()
                    .map(modelToBoConverter::directorModelToBo).toList();

            if (directorBOList.isEmpty()) {
                throw new EmptyException(ConstantMessages.NODIRECTORS);
            }

            return directorBOList;
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<DirectorBO> findAllCriteria(Pageable pageable) throws ServiceException {
        try {
            //Búsqueda de los todos lo directors, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            Page<Director> directorPage = directorRepositoryCriteria.findAllDirector(pageable);

            if (directorPage.isEmpty()) {
                throw new EmptyException(ConstantMessages.NODIRECTORS);
            }

            List<DirectorBO> directorBOList = directorPage.stream().map(modelToBoConverter::directorModelToBo).toList();

            return new PageImpl<>(directorBOList, directorPage.getPageable(), directorPage.getTotalPages());
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<DirectorBO> findAllCriteriaFilter(Pageable pageable, List<String> names, List<Integer> ages,
            List<String> nationalities) throws ServiceException {
        try {
            //Búsqueda de los todos los directores, se recorre la lista, se mapea a objeto bo y se convierte el resultado en lista
            Page<Director> directorPage = directorRepositoryCriteria.findAllFilter(pageable, names, ages,
                    nationalities);

            if (directorPage.isEmpty()) {
                throw new EmptyException(ConstantMessages.NODIRECTORS);
            }

            List<DirectorBO> directorBOList = directorPage.stream().map(modelToBoConverter::directorModelToBo).toList();

            return new PageImpl<>(directorBOList, directorPage.getPageable(), directorPage.getTotalPages());
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }

    @Override
    public void deleteByIdCriteria(long id) throws ServiceException {
        try {
            //Comprobar si existe el director con el id pasado
            if (directorRepositoryCriteria.findDirectorById(id).isEmpty()) {
                log.error("EntityNotFoundException");
                throw new EntityNotFoundException(ConstantMessages.ERRORPERSON);
            }

            directorRepositoryCriteria.deleteDirectorById(directorRepositoryCriteria.findDirectorById(id).get());
        } catch (NestedRuntimeException e) {
            log.error(ConstantMessages.ERRORSERVICE);
            throw new ServiceException(e.getLocalizedMessage());
        }
    }
}
