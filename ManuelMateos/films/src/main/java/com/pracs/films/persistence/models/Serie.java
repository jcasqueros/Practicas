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
@Table(name = "series")
@Entity
public class Serie {

    @Id
    @Column(name = "serie_id")
    private long id;

    @Column(name = "serie_title")
    private String title;

    @Column(name = "serie_year")
    private int year;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_director", nullable = false)
    private Director director;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_producer", nullable = false)
    private Producer producer;

    @ManyToMany
    @JoinTable(name = "actors_series", joinColumns = { @JoinColumn(name = "serie_id") }, inverseJoinColumns = {
            @JoinColumn(name = "actor_id") })
    private List<Actor> actors;
}
