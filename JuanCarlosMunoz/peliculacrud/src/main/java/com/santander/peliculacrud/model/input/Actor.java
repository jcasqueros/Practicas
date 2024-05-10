package com.santander.peliculacrud.model.input;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Actor.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Actor {

    @Id
    @GeneratedValue
    Long id;
    @NotNull
    @Pattern(regexp = "\\S+.*", message = "Name cannot be empty or contain only spaces")
    String name;
    @NotNull
    @Min(18)
    int age;
    @NotNull
    @Pattern(regexp = "\\S+.*", message = "Nation cannot be empty or contain only spaces")
    String nation;

}
