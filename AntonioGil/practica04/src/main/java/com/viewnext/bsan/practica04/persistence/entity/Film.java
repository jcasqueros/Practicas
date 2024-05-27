package com.viewnext.bsan.practica04.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

/**
 * The {@code Film} entity class represents the films registered in the system at the persistence layer level.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FILMS")
public class Film {

    @Id
    @Column(name = "FILM_ID")
    Long id;

    @Column(name = "FILM_TITLE")
    String title;

    @Column(name = "FILM_YEAR")
    int year;

    @ManyToOne
    @JoinColumn(name = "FILM_DIRECTOR_ID")
    Director director;

    @ManyToOne
    @JoinColumn(name = "FILM_PRODUCTION_COMPANY_ID")
    ProductionCompany productionCompany;

    @ManyToMany
    @JoinTable(name = "ACTOR_FILM_PARTICIPATIONS", joinColumns = @JoinColumn(name = "FILM_ID"),
            inverseJoinColumns = @JoinColumn(name = "ACTOR_ID"))
    Set<Actor> actors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(id, film.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
