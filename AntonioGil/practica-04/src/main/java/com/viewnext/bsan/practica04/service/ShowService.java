package com.viewnext.bsan.practica04.service;

import com.viewnext.bsan.practica04.bo.ShowBo;
import com.viewnext.bsan.practica04.util.request.QueryOptions;
import com.viewnext.bsan.practica04.util.request.WatchableFilter;

import java.util.List;
import java.util.Optional;

/**
 * The {@code ShowService} interface is a service class that defines business logic for operations with shows.
 *
 * @author Antonio Gil
 */
public interface ShowService {

    List<ShowBo> getAll(WatchableFilter filter, QueryOptions queryOptions);

    ShowBo getById(Optional<Boolean> useCustomRepository);

    ShowBo create(ShowBo film, Optional<Boolean> useCustomRepository);

    ShowBo update(long id, ShowBo show, Optional<Boolean> useCustomRepository);

    void validateShow(ShowBo show);

    void validateTitle(String title);

    void validateYear(int year);

    void deleteById(long id, Optional<Boolean> useCustomRepository);

}
