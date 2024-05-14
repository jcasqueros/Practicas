package com.viewnext.bsan.practica04.service;

import com.viewnext.bsan.practica04.bo.FilmBo;
import com.viewnext.bsan.practica04.util.request.QueryOptions;
import com.viewnext.bsan.practica04.util.request.WatchableFilter;

import java.util.List;
import java.util.Optional;

/**
 * The {@code FilmService} interface is a service class that defines business logic for operations with films.
 *
 * @author Antonio Gil
 */
public interface FilmService {

    List<FilmBo> getAll(WatchableFilter filter, QueryOptions queryOptions);

    FilmBo getById(Optional<Boolean> useCustomRepository);

    FilmBo create(FilmBo film, Optional<Boolean> useCustomRepository);

    FilmBo update(long id, FilmBo film, Optional<Boolean> useCustomRepository);

    void validateFilm(FilmBo film);

    void validateTitle(String title);

    void validateYear(int year);

    void deleteById(long id, Optional<Boolean> useCustomRepository);

}
