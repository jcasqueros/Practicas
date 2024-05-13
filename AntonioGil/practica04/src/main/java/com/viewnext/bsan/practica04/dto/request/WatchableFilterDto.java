package com.viewnext.bsan.practica04.dto.request;

import lombok.*;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.BindParam;

/**
 * The {@code WatchableFilterDto} class is a DTO (data transfer object) class that contains filtering options for read
 * queries when working with films or shows.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class WatchableFilterDto {

    String title;

    int year;

}
