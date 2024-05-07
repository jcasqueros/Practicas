package com.viewnext.films.persistencelayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entity class that represents the "PRODUCTION" table. It is an abstract class that is used for classes to inherit
 * {@link Serie} and {@link Film}.
 *
 * @author Franciosco Balonero Olivera
 * @see Serie
 * @see Film
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTION_SEQ")
    private long id;

    private String title;

    private int releaseYear;

    @ManyToOne
    private Director director;

    @ManyToOne
    private Producer producer;

    @ManyToMany
    @JoinTable(name = "production_actor", joinColumns = @JoinColumn(name = "production_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actors;
}




