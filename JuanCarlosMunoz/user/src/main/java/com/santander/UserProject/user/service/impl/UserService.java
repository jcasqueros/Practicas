package com.santander.UserProject.user.service.impl;

import com.santander.UserProject.user.model.bo.UserBO;
import com.santander.UserProject.user.model.entity.Users;
import com.santander.UserProject.user.model.repository.UserRepository;
import com.santander.UserProject.user.service.UserServiceInterface;
import com.santander.UserProject.user.util.exception.GenericException;
import com.santander.UserProject.user.util.mapper.bo.UserBOMapper;
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
 * The type Users service.
 */
@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;
    private final UserBOMapper userBOMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Instantiates a new Users service.
     *
     * @param userBOMapper
     *         the user bo mapper
     * @param userRepository
     *         the user repository
     */
    @Autowired
    public UserService(UserBOMapper userBOMapper, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userBOMapper = userBOMapper;

    }

    @Override
    public UserBO createUser(UserBO userBO) throws GenericException {
        Users users;

        users = userBOMapper.boToEntity(userBO);

        try {
            users = userRepository.save(users);
            userBO = userBOMapper.entityToBo(users);

        } catch (Exception e) {
            throw new GenericException("Failed to create users: ", e);
        }

        return userBO;

    }



    @Override
    public UserBO getUserById(long id) {
        Users users = userRepository.findById(id).orElse(null);
        UserBO userBO = null;
        if (users != null) {
            userBO = userBOMapper.entityToBo(users);
        }

        return userBO;
    }

    @Override
    public boolean updateUser(long id, UserBO userBO) throws GenericException {
        boolean update = false;
        if (userRepository.existsById(id)) {

            try {
                Users users = userBOMapper.boToEntity(userBO);
                users.setId(id);
                userRepository.save(users);
                update = userRepository.existsById(id);

            } catch (Exception e) {

                throw new GenericException("Failed to update user: ", e);

            }

        } else {
            userNotfound();
        }
        return update;
    }

    @Override
    public boolean deleteUser(long id) throws GenericException {

        boolean delete = false;
        if (userRepository.existsById(id)) {
            try {
                userRepository.deleteById(id);

                delete = true;

            } catch (Exception e) {
                throw new GenericException("Failed to delete user: {}", e);
            }

        } else {
            userNotfound();
        }

        return delete;
    }

    @Override
    public List<UserBO> getUserByNameAge(String name,int age, int page){
        Pageable pageable = PageRequest.of(page, 5);
        Page<Users> usersPage = userRepository.findAllByNameAndAge(name,age, pageable);
        List<UserBO> users = userBOMapper.listEntitytoListBo(usersPage);

        return users.stream().sorted(Comparator.comparingInt(UserBO::getAge)).toList();
    }


    private void userNotfound() throws GenericException {
        logger.error("Users not found");
        throw new GenericException("Users not found");

    }

}
