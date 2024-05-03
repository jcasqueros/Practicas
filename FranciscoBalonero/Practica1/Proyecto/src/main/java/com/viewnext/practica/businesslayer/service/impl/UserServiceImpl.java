package com.viewnext.practica.businesslayer.service.impl;

import com.viewnext.practica.businesslayer.bo.UserBO;
import com.viewnext.practica.businesslayer.exception.BusinessException;
import com.viewnext.practica.businesslayer.exception.InvalidDniException;
import com.viewnext.practica.businesslayer.exception.UserNotFoundException;
import com.viewnext.practica.businesslayer.service.UserService;
import com.viewnext.practica.persistencelayer.dao.UserDAO;
import com.viewnext.practica.persistencelayer.exception.EntityNotFoundException;
import com.viewnext.practica.persistencelayer.exception.PersistenceLayerException;
import com.viewnext.practica.util.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the UserService interface.
 *
 * @author Francisco Balonero Olivera
 * @see UserService
 * @see Converter
 * @see UserDAO
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final Converter converter;

    @Override
    public UserBO save(UserBO userBO) throws BusinessException {
        try {
            if (userBO.validateDNI()) {
                return converter.userEntityToBO(userDAO.save(converter.userBOToEntity(userBO)));
            } else {
                throw new InvalidDniException("Invalid DNI");
            }
        } catch (PersistenceLayerException e) {
            throw new BusinessException("User not saved", e);
        }
    }

    @Override
    public List<UserBO> getAll() throws BusinessException {
        try {
            return userDAO.findAllUsers().stream().map(converter::userEntityToBO).toList();
        } catch (EntityNotFoundException enf) {
            throw new UserNotFoundException("Users not found", enf);
        } catch (PersistenceLayerException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public UserBO getUserByDni(String dni) throws BusinessException {
        try {
            return converter.userEntityToBO(userDAO.findUserByDni(dni));
        } catch (EntityNotFoundException enf) {
            throw new UserNotFoundException("User not found", enf);
        } catch (PersistenceLayerException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void deleteUser(String dni) throws BusinessException {
        try {
            userDAO.deleteUserByDni(dni);
        } catch (EntityNotFoundException enf) {
            throw new UserNotFoundException("User not found", enf);
        } catch (PersistenceLayerException e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public UserBO updateUser(UserBO userBO) throws BusinessException {
        try {
            return converter.userEntityToBO(userDAO.updateUser(converter.userBOToEntity(userBO)));
        } catch (EntityNotFoundException enf) {
            throw new UserNotFoundException("User not found", enf);
        } catch (PersistenceLayerException e) {
            throw new BusinessException(e);
        }
    }
}
