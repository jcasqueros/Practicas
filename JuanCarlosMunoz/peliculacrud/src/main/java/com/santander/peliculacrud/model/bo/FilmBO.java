package com.santander.peliculacrud.model.bo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Film show.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmBO {

    private long id;

    private String title;

    private int created;

    private List<ActorBO> actors;

    private DirectorBO director;
}
