package com.viewnext.films.presentationlayer.dto;

import com.viewnext.films.businesslayer.bo.ActorBO;
import com.viewnext.films.businesslayer.bo.DirectorBO;
import com.viewnext.films.businesslayer.bo.ProducerBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmOutDTO {
    private long id;

    private String title;

    private int releaseYear;

    private DirectorBO director;

    private ProducerBO producer;

    private List<ActorBO> actors;
}
