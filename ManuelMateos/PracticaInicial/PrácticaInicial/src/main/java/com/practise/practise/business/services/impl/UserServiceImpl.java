package com.practise.practise.business.services.impl;

import com.practise.practise.business.bo.UserBO;
import com.practise.practise.business.converters.BoToModelConverter;
import com.practise.practise.business.converters.ModelToBoConverter;
import com.practise.practise.business.services.UserService;
import com.practise.practise.exceptions.DataAccessException;
import com.practise.practise.exceptions.ServiceException;
import com.practise.practise.exceptions.UpsertException;
import com.practise.practise.persistence.dao.UserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the interface {@link UserService}
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private String errorService = "Error in service layer";

    private final UserDAO userDAO;

    private final ModelToBoConverter modelToBoConverter;
    private final BoToModelConverter boToModelConverter;

    @Override
    public List<UserBO> findAll() throws ServiceException {
        try {
            return userDAO.findAll().stream().map(modelToBoConverter::userModelToUserBo).toList();
        } catch (DataAccessException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public UserBO findById(String dni) throws ServiceException {
        try {
            return modelToBoConverter.userModelToUserBo(userDAO.findById(dni));
        } catch (DataAccessException e) {
            log.error("Error en la capa de servicio");
            throw new ServiceException(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public UserBO save(UserBO userBO) throws ServiceException {
        try {
            return modelToBoConverter.userModelToUserBo(userDAO.save(boToModelConverter.userBoToUserModel(userBO)));
        } catch (DataAccessException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public UserBO update(UserBO userBO) throws ServiceException, UpsertException {
        try {
            UserBO savedUserBO = modelToBoConverter.userModelToUserBo(userDAO.findById(userBO.getDni()));
            savedUserBO.setName(userBO.getName());
            savedUserBO.setSurname(userBO.getSurname());
            savedUserBO.setAge(userBO.getAge());
            return modelToBoConverter.userModelToUserBo(
                    userDAO.update(boToModelConverter.userBoToUserModel(savedUserBO)));
        } catch (DataAccessException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage(), e);
        }
    }

    @Override
    public void delete(String dni) throws ServiceException {
        try {
            userDAO.deleteById(dni);
        } catch (DataAccessException e) {
            log.error(errorService);
            throw new ServiceException(e.getLocalizedMessage(), e);
        }
    }
}
