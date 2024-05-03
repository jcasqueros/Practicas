package com.viewnext.practica.presentationlayer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * DTO representing user information.
 *
 * @author Franciosco Balonero Olivera
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "DTO representing user information")
public class UserDTO {

    @Schema(description = "DNI of the user", example = "07256630S")
    private String dni;

    @Schema(description = "User's name", example = "Francisco")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Schema(description = "User's surname", example = "Balonero")
    @NotBlank(message = "Surname cannot be blank")
    private String surname;
    
    @Schema(description = "Age of the user", example = "99")
    private int age;
}
