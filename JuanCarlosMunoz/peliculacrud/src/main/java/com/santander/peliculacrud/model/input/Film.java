package com.santander.peliculacrud.model.input;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

/**
 * The type Film.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Film {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Pattern(regexp = "\\S+.*", message = "Title cannot be empty or contain only spaces")
    private String title;

    @NotNull
    private int created;

    @ManyToMany
    @Fetch(FetchMode.JOIN)
    private List<Actor> actors;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @Fetch(FetchMode.JOIN)
    private Director director;
}
