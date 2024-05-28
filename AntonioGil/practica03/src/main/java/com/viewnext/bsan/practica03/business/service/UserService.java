package com.viewnext.bsan.practica03.business.service;

import com.viewnext.bsan.practica03.business.bo.UserBo;

import java.util.List;

public interface UserService {

    List<UserBo> getAll();

    UserBo getByDni(String dni);

    UserBo create(UserBo user);

    UserBo update(String dni, UserBo user);

    void validateUser(UserBo user);

    void validateDni(String dni);

    void validateName(String name);

    void validateSurname(String surname);

    void validateAge(int age);

    void deleteByDni(String dni);

}
