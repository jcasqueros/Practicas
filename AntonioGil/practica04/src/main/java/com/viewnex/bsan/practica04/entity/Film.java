package com.viewnex.bsan.practica04.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

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
    short year;

    @ManyToOne
    @JoinColumn(name = "FILM_DIRECTOR_ID")
    Director director;

    @ManyToOne
    @JoinColumn(name = "FILM_PRODUCTION_COMPANY_ID")
    ProductionCompany productionCompany;

    @ManyToMany
    @JoinTable(name = "ACTOR_FILM_PARTICIPATION", joinColumns = @JoinColumn(name = "FILM_ID"),
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