package com.pracs.films.persistence.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entity Production
 *
 * @author Manuel Mateos de Torres
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productions")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTION_SEQ")
    private long id;

    private String title;

    private int debut;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "director_id", nullable = false)
    private Director director;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "producer_id", nullable = false)
    private Producer producer;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "production_actors", joinColumns = { @JoinColumn(name = "production_id") }, inverseJoinColumns = {
            @JoinColumn(name = "actor_id") })
    private List<Actor> actors;
}
