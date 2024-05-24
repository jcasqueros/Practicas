package com.santander.peliculacrud.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Director dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DirectorDTO {
    @NotNull(message = "Name can´t be null")
    @Pattern(regexp = "\\S+.*", message = "Name cannot be empty or contain only spaces")
    private String name;

    @NotNull(message = "Age can´t be null")
    @Min(value = 18, message = "Age must be greater or equal to 18")
    private int age;

    @NotNull(message = "Nation can´t be null")
    @Pattern(regexp = "\\S+.*", message = "Nation cannot be empty or contain only spaces")
    private String nation;
}

