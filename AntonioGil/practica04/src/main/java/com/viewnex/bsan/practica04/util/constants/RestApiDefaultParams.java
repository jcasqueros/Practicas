package com.viewnex.bsan.practica04.util.constants;

import org.springframework.data.domain.Sort;

public class RestApiDefaultParams {

    private RestApiDefaultParams() {
    }

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

    public static final String DEFAULT_SORTING_FIELD = "id";

}
