package com.viewnext.films.businesslayer.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The Film business object.
 *
 * @author Francisco Balonero Olivera
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmBO {
    private long id;

    private String title;

    private int releaseYear;

    private DirectorBO director;

    private ProducerBO producer;

    private List<ActorBO> actors;
}
