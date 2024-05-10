package com.santander.peliculacrud.model.output;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

/**
 * The type Film out.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmModelController {

    @NotNull(message = "Title can´t be null")
    @NotEmpty
    @Pattern(regexp = "[^\\s]+.*", message = "Title cannot be empty or contain only spaces")
    private String title;

    @NotNull(message = "Created can´t be null")
    @Min(value = 1900, message = "Created must be grater or equal to 1900")
    private int created;

    @NotNull(message = "idActor can´t be null")
    private List<Long> idActor;

    @NotNull(message = "idActor can´t be null")
    private Long idDirector;
}
