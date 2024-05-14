package com.viewnext.bsan.practica04.service;

import com.viewnext.bsan.practica04.bo.DirectorBo;
import com.viewnext.bsan.practica04.util.request.PersonFilter;
import com.viewnext.bsan.practica04.util.request.QueryOptions;

import java.util.List;
import java.util.Optional;

/**
 * The {@code DirectorService} interface is a service class that defines business logic for operations with directors.
 *
 * @author Antonio Gil
 */
public interface DirectorService {

    List<DirectorBo> getAll(PersonFilter filter, QueryOptions queryOptions);

    DirectorBo getById(Optional<Boolean> useCustomRepository);

    DirectorBo create(DirectorBo director, Optional<Boolean> useCustomRepository);

    DirectorBo update(long id, DirectorBo director, Optional<Boolean> useCustomRepository);

    void validateDirector(DirectorBo director);

    void validateName(String name);

    void validateAge(int age);

    void validateNationality(String nationality);

    void deleteById(long id, Optional<Boolean> useCustomRepository);

}
