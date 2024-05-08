package com.viewnext.films.presentationlayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorInDTO {

    private String name;

    private int age;

    private String nationality;
}
