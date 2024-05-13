package com.viewnext.bsan.practica04.service;

import com.viewnext.bsan.practica04.bo.DirectorBo;
import com.viewnext.bsan.practica04.dto.request.PersonFilterDto;
import com.viewnext.bsan.practica04.dto.request.QueryOptionsDto;
import com.viewnext.bsan.practica04.exception.service.BadInputDataException;
import com.viewnext.bsan.practica04.exception.service.DuplicateUniqueFieldException;
import com.viewnext.bsan.practica04.exception.service.MissingRequiredFieldException;
import com.viewnext.bsan.practica04.exception.service.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * The {@code DirectorService} interface is a service class that defines business logic for operations with directors.
 *
 * @author Antonio Gil
 */
public interface DirectorService {

    List<DirectorBo> getAll(PersonFilterDto filter, QueryOptionsDto queryOptions);

    DirectorBo getById(Optional<Boolean> useCustomRepository);

    DirectorBo create(DirectorBo director, Optional<Boolean> useCustomRepository);

    DirectorBo update(long id, DirectorBo director, Optional<Boolean> useCustomRepository);

    void validateDirector(DirectorBo director);

    void validateName(String name);

    void validateAge(int age);

    void validateNationality(String nationality);

    void deleteById(long id, Optional<Boolean> useCustomRepository);

}
