package com.viewnext.bsan.practica04.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.BindParam;

import java.util.Optional;

/**
 * The {@code ProductionCompanyFilter} class contains filtering options for read queries when working with production
 * companies. These objects are meant to be passed as parameters to controllers and services.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProductionCompanyFilter {

    Optional<String> name;

    @BindParam("year_founded")
    Optional<Integer> yearFounded;

    public boolean isEmpty() {
        return (name.isEmpty() && yearFounded.isEmpty());
    }

}
