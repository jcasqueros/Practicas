package com.viewnext.bsan.practica04.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

/**
 * The {@code PersonFilter} class contains filtering options for read queries when working with actors or directors.
 * These objects are meant to be passed to controllers and services.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class PersonFilter {

    Optional<String> name;

    Optional<Integer> age;

    Optional<String> nationality;

    public boolean isEmpty() {
        return (name.isEmpty() && age.isEmpty() && nationality.isEmpty());
    }

}
