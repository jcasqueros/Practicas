package com.viewnex.bsan.practica04.dto.request;

import lombok.*;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProductionCompanyFilterDto {

    String name;

    @BindParam("year_founded")
    int yearFounded;

}
