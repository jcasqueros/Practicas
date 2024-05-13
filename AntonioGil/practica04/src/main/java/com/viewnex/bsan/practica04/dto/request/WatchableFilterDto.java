package com.viewnex.bsan.practica04.dto.request;

import lombok.*;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class WatchableFilterDto {

    String title;

    int year;

}
