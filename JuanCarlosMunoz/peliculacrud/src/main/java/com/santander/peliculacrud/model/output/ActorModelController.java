package com.santander.peliculacrud.model.output;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Actor out.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActorModelController {

    @NotNull(message = "Name can´t be null")
    @Pattern(regexp = "[^\\s]+.*", message = "Name cannot be empty or contain only spaces")
    private String name;

    @NotNull(message = "Age can´t be null")
    @Min(value = 18, message = "Age must be greater or equal to 18")
    private int age;

    @NotNull(message = "Nation can´t be null")
    @Pattern(regexp = "[^\\s]+.*", message = "Nation cannot be empty or contain only spaces")
    private String nation;
}

