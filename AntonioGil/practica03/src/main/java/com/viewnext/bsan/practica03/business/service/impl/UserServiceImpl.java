package com.viewnext.bsan.practica03.business.service.impl;

import com.viewnext.bsan.practica03.business.bo.UserBo;
import com.viewnext.bsan.practica03.business.exception.BadInputDataException;
import com.viewnext.bsan.practica03.business.exception.MissingRequiredFieldException;
import com.viewnext.bsan.practica03.business.exception.ResourceNotFoundException;
import com.viewnext.bsan.practica03.business.service.UserService;
import com.viewnext.bsan.practica03.persistence.entity.User;
import com.viewnext.bsan.practica03.business.exception.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica03.persistence.repository.UserRepository;
import com.viewnext.bsan.practica03.util.mapper.ServiceLevelUserMapper;
import com.viewnext.bsan.practica03.util.constants.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class.getCanonicalName());

    private final UserRepository repository;
    private final ServiceLevelUserMapper mapper;

    public UserServiceImpl(UserRepository repository, ServiceLevelUserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<UserBo> getAll() {
        return repository.findAll().stream().map(mapper::entityToBo).toList();
    }

    @Override
    public UserBo getByDni(String dni) {
        Optional<User> foundEntity = repository.findById(dni);

        if (foundEntity.isEmpty()) {
            String message = MessageFormat.format(Messages.USER_NOT_FOUND, dni);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        return mapper.entityToBo(foundEntity.orElseThrow());
    }

    @Override
    public UserBo create(UserBo user) {
        validateUser(user);

        if (repository.existsById(user.getDni())) {
            String message = MessageFormat.format("A user with DNI=\"{0}\" already exists", user.getDni());
            LOGGER.warn(message);
            throw new DuplicateUniqueFieldException(message, "id");
        }

        User entity = mapper.boToEntity(user);
        repository.save(entity);

        return mapper.entityToBo(entity);
    }

    @Override
    public UserBo update(String dni, UserBo user) {
        validateUser(user);
        user.setDni(dni);

        if (!repository.existsById(dni)) {
            String message = MessageFormat.format(Messages.USER_NOT_FOUND, dni);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        User entity = mapper.boToEntity(user);
        repository.save(entity);

        return mapper.entityToBo(entity);
    }

    @Override
    public void validateUser(UserBo user) {
        if (user == null) {
            String message = "user cannot be null";
            LOGGER.warn(message);
            throw new MissingRequiredFieldException(message, "user");
        }

        validateDni(user.getDni());
        validateName(user.getName());
        validateSurname(user.getSurname());
        validateAge(user.getAge());
    }

    @Override
    public void validateDni(String dni) {
        if (dni == null || dni.isBlank()) {
            String message = "dni is a required field";
            LOGGER.warn(message);
            throw new MissingRequiredFieldException(message, "dni");
        }
    }

    @Override
    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            String message = "name is a required field";
            LOGGER.warn(message);
            throw new MissingRequiredFieldException(message, "name");
        }
    }

    @Override
    public void validateSurname(String surname) {
        if (surname == null || surname.isBlank()) {
            String message = "surname is a required field";
            LOGGER.warn(message);
            throw new MissingRequiredFieldException(message, "surname");
        }
    }

    @Override
    public void validateAge(int age) {
        if (age < 0) {
            String message = "age cannot be a negative number";
            LOGGER.warn(message);
            throw new BadInputDataException(message);
        }
    }

    @Override
    public void deleteByDni(String dni) {
        if (dni == null) {
            String message = "dni cannot be null";
            LOGGER.warn(message);
            throw new MissingRequiredFieldException(message, "dni");
        }

        if (!repository.existsById(dni)) {
            String message = MessageFormat.format(Messages.USER_NOT_FOUND, dni);
            LOGGER.warn(message);
            throw new ResourceNotFoundException(message);
        }

        repository.deleteById(dni);
    }
}
