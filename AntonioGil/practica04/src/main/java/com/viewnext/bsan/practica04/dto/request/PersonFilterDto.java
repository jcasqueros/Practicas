package com.viewnext.bsan.practica04.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

/**
 * The {@code PersonFilterDto} class is a DTO (data transfer object) class that contains filtering options for read
 * queries when working with actors or directors.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class PersonFilterDto {

    Optional<String> name;

    Optional<Integer> age;

    Optional<String> nationality;

}
