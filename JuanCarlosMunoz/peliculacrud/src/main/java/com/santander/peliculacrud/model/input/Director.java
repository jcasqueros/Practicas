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
 * The type Director.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Director {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Pattern(regexp = "[^\\s]+.*", message = "Name cannot be empty or contain only spaces")
    private String name;
    @NotNull
    @Min(18)
    private int age;
    @NotNull
    @Pattern(regexp = "[^\\s]+.*", message = "Nation cannot be empty or contain only spaces")
    private String nation;


}
