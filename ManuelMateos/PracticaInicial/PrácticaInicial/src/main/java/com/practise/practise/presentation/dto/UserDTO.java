package com.practise.practise.presentation.dto;

import com.practise.practise.persistence.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Tranfer Object of {@link User}
 *
 * @author Manuel Mateos de Torres
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDTO {

    private String dni;

    private String name;

    private String surname;

    private int age;
}

