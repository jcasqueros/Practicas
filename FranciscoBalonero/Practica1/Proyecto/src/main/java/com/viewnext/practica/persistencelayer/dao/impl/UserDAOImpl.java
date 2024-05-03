package com.viewnext.practica.persistencelayer.dao.impl;

import com.viewnext.practica.persistencelayer.dao.UserDAO;
import com.viewnext.practica.persistencelayer.entity.User;
import com.viewnext.practica.persistencelayer.exception.EntityNotFoundException;
import com.viewnext.practica.persistencelayer.exception.PersistenceLayerException;
import com.viewnext.practica.persistencelayer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserDAO interface implementation
 *
 * @author Francisco Balonero Olivera
 * @see UserDAO
 */
@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    @Override
    public User save(User user) throws PersistenceLayerException {
        try {
            if (userRepository.existsById(user.getDni())) {
                throw new PersistenceLayerException("That user already exists");
            } else {
                userRepository.saveUser(user.getDni(), user.getName(), user.getSurname(), user.getAge());
                return findUserByDni(user.getDni());
            }
        } catch (ConstraintViolationException | NestedRuntimeException e) {
            throw new PersistenceLayerException("User not saved", e);
        }
    }

    @Override
    public List<User> findAllUsers() throws PersistenceLayerException {
        try {
            List<User> users = userRepository.findAllUsers();
            if (users.isEmpty()) {
                throw new EntityNotFoundException("That user does not exist");
            } else {
                return users;
            }
        } catch (NestedRuntimeException e) {
            throw new PersistenceLayerException(e);
        }
    }

    @Override
    public User findUserByDni(String dni) throws PersistenceLayerException {
        try {
            return userRepository.findUserByDni(dni).orElseThrow(() -> new EntityNotFoundException("User not found"));
        } catch (NestedRuntimeException e) {
            throw new PersistenceLayerException(e);
        }
    }

    @Override
    public void deleteUserByDni(String dni) throws PersistenceLayerException {
        try {
            if (userRepository.existsById(dni)) {
                int result = userRepository.deleteUserByDni(dni);
                if (result != 1) {
                    throw new PersistenceLayerException("User not deleted");
                }
            } else {
                throw new EntityNotFoundException("That user does not exist");
            }
        } catch (NestedRuntimeException e) {
            throw new PersistenceLayerException(e);
        }
    }

    @Override
    public User updateUser(User user) throws PersistenceLayerException {
        try {
            if (userRepository.existsById(user.getDni())) {
                int result = userRepository.updateUser(user.getDni(), user.getName(), user.getSurname(), user.getAge());
                if (result == 1) {
                    return user;
                } else {
                    throw new PersistenceLayerException("User not updated");
                }
            } else {
                throw new EntityNotFoundException("That user does not exist");
            }
        } catch (NestedRuntimeException e) {
            throw new PersistenceLayerException(e);
        }
    }
}
