package com.viewnext.bsan.practica03.dao.impl;

import com.viewnext.bsan.practica03.dao.UserDao;
import com.viewnext.bsan.practica03.entity.User;
import com.viewnext.bsan.practica03.exception.dao.BadUpsertException;
import com.viewnext.bsan.practica03.exception.dao.DaoLevelException;
import com.viewnext.bsan.practica03.repository.UserRepository;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementation for the {@code UserDao} interface.
 *
 * @author Antonio Gil
 */
@Repository
public class UserDaoImpl implements UserDao {

    private final UserRepository repository;

    public UserDaoImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAll() throws DaoLevelException {
        try {
            return repository.findAll();
        } catch (NestedRuntimeException ex) {
            throw new DaoLevelException("UserDao#getAll failed", ex);
        }
    }

    @Override
    public Optional<User> getByDni(String dni) throws DaoLevelException {
        try {
            if (dni == null) {
                return Optional.empty();
            }
            return repository.findById(dni);
        } catch (NestedRuntimeException ex) {
            throw new DaoLevelException("UserDao#getByDni failed", ex);
        }
    }

    @Override
    public User create(User user) throws DaoLevelException {
        try {
            return repository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new BadUpsertException("UserDao#create failed due to integrity constraint violation(s)", ex);
        } catch (NestedRuntimeException ex) {
            throw new DaoLevelException("UserDao#create failed", ex);
        }
    }

    @Override
    public User update(User user) throws DaoLevelException {
        try {
            if (!repository.existsById(user.getDni())) {
                throw new BadUpsertException("UserDao#update failed: Cannot update a nonexistent entity");
            }
            return repository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new BadUpsertException("UserDao#update failed: Integrity constraint violation(s) detected", ex);
        } catch (NestedRuntimeException ex) {
            throw new DaoLevelException("UserDao#update failed", ex);
        }
    }

    @Override
    public void deleteByDni(String dni) throws DaoLevelException {
        try {
            if (dni != null) {
                repository.deleteById(dni);
            }
        } catch (NestedRuntimeException ex) {
            throw new DaoLevelException("UserDao#deleteByDni failed", ex);
        }
    }

}
