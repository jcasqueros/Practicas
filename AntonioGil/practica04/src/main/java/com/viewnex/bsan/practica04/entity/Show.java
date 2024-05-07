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
@Table(name = "SHOWS")
public class Show {

    @Id
    @Column(name = "SHOW_ID")
    Long id;

    @Column(name = "SHOW_TITLE")
    String title;

    @Column(name = "SHOW_YEAR")
    int year;

    @ManyToOne
    @JoinColumn(name = "SHOW_DIRECTOR_ID")
    Director director;

    @ManyToOne
    @JoinColumn(name = "SHOW_PRODUCTION_COMPANY_ID")
    ProductionCompany productionCompany;

    @ManyToMany
    @JoinTable(name = "ACTOR_SHOW_PARTICIPATIONS", joinColumns = @JoinColumn(name = "SHOW_ID"),
            inverseJoinColumns = @JoinColumn(name = "ACTOR_ID"))
    Set<Actor> actors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return Objects.equals(id, show.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
