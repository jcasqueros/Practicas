package com.viewnext.bsan.practica04.util.constants;

import org.springframework.data.domain.Sort;

/**
 * Literal constants that define default parameters for the REST API calls.
 *
 * @author Antonio Gil
 */
public class RestApiDefaultParams {

    private RestApiDefaultParams() {
    }

    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

    public static final String DEFAULT_SORTING_FIELD = "id";

}
