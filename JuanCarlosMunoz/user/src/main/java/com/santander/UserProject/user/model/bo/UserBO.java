package com.santander.UserProject.user.model.bo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBO {

    private Long id;
    private String name;
    private int age;


}
