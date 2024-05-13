package com.viewnext.bsan.practica04.dto.request;

import lombok.*;
import org.springframework.web.bind.annotation.BindParam;

/**
 * The {@code ProductionCompanyFilterDto} class is a DTO (data transfer object) class that contains filtering options
 * for read queries when working with production companies.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ProductionCompanyFilterDto {

    String name;

    @BindParam("year_founded")
    int yearFounded;

}
