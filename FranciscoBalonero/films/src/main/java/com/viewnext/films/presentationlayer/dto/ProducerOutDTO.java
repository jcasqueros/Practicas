package com.viewnext.films.presentationlayer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProducerOutDTO {
    private long id;

    private String name;

    private int foundationYear;
}
