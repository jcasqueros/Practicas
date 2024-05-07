package com.viewnext.films.businesslayer.bo;

import com.viewnext.films.persistencelayer.entity.Actor;
import com.viewnext.films.persistencelayer.entity.Director;
import com.viewnext.films.persistencelayer.entity.Producer;

import java.util.List;

public class SerieBO {

    private long id;

    private String title;

    private int year;

    private Director director;

    private Producer producer;

    private List<Actor> actors;
}
