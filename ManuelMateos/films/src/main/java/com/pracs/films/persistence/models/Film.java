package com.pracs.films.persistence.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "films")
@Entity
public class Film {

    @Id
    @Column(name = "film_id")
    private long id;

    @Column(name = "film_title")
    private String title;

    @Column(name = "film_year")
    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_director", nullable = false)
    private Director director;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_producer", nullable = false)
    private Producer producer;

    @ManyToMany
    @JoinTable(name = "actors_films", joinColumns = { @JoinColumn(name = "film_id") }, inverseJoinColumns = {
            @JoinColumn(name = "actor_id") })
    private List<Actor> actors;
}
