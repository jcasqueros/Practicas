package com.viewnext.films.persistencelayer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private int year;

    @ManyToOne
    private Director director;

    @ManyToOne
    private Producer producer;

    @ManyToMany
    @JoinTable(name = "production_actor", joinColumns = @JoinColumn(name = "production_id"), inverseJoinColumns = @JoinColumn(name = "actor_id"))
    private List<Actor> actors;
}




