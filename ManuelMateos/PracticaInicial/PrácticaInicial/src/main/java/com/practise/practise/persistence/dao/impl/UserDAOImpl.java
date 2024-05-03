package com.practise.practise.persistence.dao.impl;

import com.practise.practise.exceptions.*;
import com.practise.practise.persistence.dao.UserDAO;
import com.practise.practise.persistence.models.User;
import com.practise.practise.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Impelmetation of {@link UserDAO}
 *
 * @author Manuel Mateos de Torres
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    String errorDB = "Error in database";

    @Override
    public List<User> findAll() throws DataAccessException {
        List<User> users = new ArrayList<>();

        try {
            users = userRepository.findAllUsers();
        } catch (NestedRuntimeException nre) {
            log.error(errorDB);
            throw new DataAccessException(errorDB, nre);
        }

        if (users.isEmpty()) {
            throw new EmptyException("Not users found");
        }

        return users;
    }

    @Override
    public User findById(String dni) throws DataAccessException {
        try {
            return userRepository.findUserById(dni).orElseThrow(() -> new EmptyException("User not found"));
        } catch (NestedRuntimeException nre) {
            log.error(errorDB);
            throw new DataAccessException(errorDB, nre);
        }
    }

    @Override
    public User save(User user) throws DataAccessException {
        try {
            if (userRepository.existsById(user.getDni())) {
                throw new DuplicatedIdException("Existing dni");
            }
            userRepository.saveUser(user.getDni(), user.getName(), user.getSurname(), user.getAge());
            log.info("User registered");
            log.debug(user.getDni() + " has been register");
            return user;
        } catch (DataIntegrityViolationException dive) {
            log.error("Insert error");
            throw new UpsertException("Insert error", dive);
        } catch (NestedRuntimeException nre) {
            log.error(errorDB);
            throw new DataAccessException(errorDB, nre);
        }
    }

    @Override
    public User update(User user) throws DataAccessException {
        try {
            userRepository.updateUser(user.getDni(), user.getName(), user.getSurname(), user.getAge());
            log.info("User updated");
            log.debug(user.getDni() + " has been update");
            return user;
        } catch (DataIntegrityViolationException dive) {
            log.error("Update error");
            throw new UpsertException("Update error", dive);
        } catch (NestedRuntimeException nre) {
            log.error(errorDB);
            throw new DataAccessException(errorDB, nre);
        }
    }

    @Override
    public void deleteById(String dni) throws DataAccessException {
        try {
            if (!userRepository.existsById(dni)) {
                throw new EntityNotFoundException("User doesnt exists");
            }
            userRepository.deleteUserById(dni);
            log.info("User deleted");
            log.debug("User " + dni + " has been delete");
        } catch (NestedRuntimeException nre) {
            log.error(errorDB);
            throw new DataAccessException(errorDB, nre);
        }
    }
}