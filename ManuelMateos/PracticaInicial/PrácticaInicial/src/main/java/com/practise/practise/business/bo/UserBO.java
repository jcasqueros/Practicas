package com.practise.practise.business.bo;

import com.practise.practise.persistence.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bussines Object of {@link User}
 *
 * @author Manuel Mateos de Torres
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserBO {

    private String dni;

    private String name;

    private String surname;

    private int age;
}
