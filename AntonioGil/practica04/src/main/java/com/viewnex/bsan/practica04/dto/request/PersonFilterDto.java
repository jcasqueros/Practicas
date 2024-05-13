package com.viewnex.bsan.practica04.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PersonFilterDto {

    Optional<String> name;

    Optional<Integer> age;

    Optional<String> nationality;

}
