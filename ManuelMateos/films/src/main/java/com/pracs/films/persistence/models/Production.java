package com.pracs.films.persistence.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productions")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Production {

    @Id
    @SequenceGenerator(name = "production_seq")
    private long id;

    private String title;

    private int debut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producer_id", nullable = false)
    private Producer producer;

    @ManyToMany
    @JoinTable(name = "production_actors", joinColumns = { @JoinColumn(name = "production_id") }, inverseJoinColumns = {
            @JoinColumn(name = "actor_id") })
    private List<Actor> actors;
}
