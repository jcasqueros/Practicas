package com.santander.peliculacrud.util;

import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.input.Director;
import com.santander.peliculacrud.model.input.Film;
import com.santander.peliculacrud.model.input.Series;
import com.santander.peliculacrud.model.output.ActorModelService;
import com.santander.peliculacrud.model.output.DirectorModelService;
import com.santander.peliculacrud.model.output.FilmModelService;
import com.santander.peliculacrud.model.output.SeriesModelService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ConverterObject {

    private final ModelMapper modelMapper;

    public ActorModelService actorModel(Actor actor) {
        return modelMapper.map(actor, ActorModelService.class);
    }

    public DirectorModelService directorModelToBo(Director director) {
        return modelMapper.map(director, DirectorModelService.class);
    }

    public FilmModelService filmModelToBo(Film film) {
        return modelMapper.map(film, FilmModelService.class);
    }

    public SeriesModelService seriesModelToBo(Series series) {
        return modelMapper.map(series, SeriesModelService.class);
    }



}
