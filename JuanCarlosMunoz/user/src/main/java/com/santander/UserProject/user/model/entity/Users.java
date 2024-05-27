package com.santander.UserProject.user.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Users.
 */
@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Pattern(regexp = "\\S+.*", message = "Name cannot be empty or contain only spaces")
    private String name;
    @NotNull
    @Min(18)
    private int age;

}
