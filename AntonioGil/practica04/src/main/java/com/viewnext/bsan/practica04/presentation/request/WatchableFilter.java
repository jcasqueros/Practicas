package com.viewnext.bsan.practica04.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

/**
 * The {@code WatchableFilter} class contains filtering options for read queries when working with films or shows.
 * These objects are meant to be passed as parameters to controllers and services.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class WatchableFilter {

    Optional<String> title;

    Optional<Integer> year;

    public boolean isEmpty() {
        return (title.isEmpty() && year.isEmpty());
    }

}
