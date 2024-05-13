package com.viewnext.bsan.practica04.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.BindParam;

import java.util.Optional;

/**
 * The {@code QueryOptionsDto} class is a DTO (data transfer object) class that contains generic options for read
 * queries, such as pagination or sorting.
 *
 * @author Antonio Gil
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class QueryOptionsDto {

    @BindParam("use_custom_repository")
    Optional<Boolean> useCustomRepository;

    @BindParam("page_size")
    Optional<Integer> pageSize;

    @BindParam("page_number")
    Optional<Integer> pageNumber;

    @BindParam("sort_by")
    Optional<String> sortingField;

    @BindParam("sort_direction")
    Optional<Sort.Direction> sortDirection;

}
