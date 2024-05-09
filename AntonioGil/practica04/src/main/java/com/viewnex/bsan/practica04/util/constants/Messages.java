package com.viewnex.bsan.practica04.util.constants;

public class Messages {

    private Messages() {}

    public static final String ACTOR_ENTITY_NAME = "actor";
    public static final String DIRECTOR_ENTITY_NAME = "director";
    public static final String FILM_ENTITY_NAME = "film";
    public static final String PRODUCTION_COMPANY_ENTITY_NAME = "production company";
    public static final String SHOW_ENTITY_NAME = "show";

    public static final String METHOD_CALLED = "Method called: {0}";

    public static final String RESOURCE_NOT_FOUND = "No {0} with ID={1} was found";
    public static final String RESOURCE_ALREADY_EXISTS = "{0} with ID={1} already exists";
    public static final String REQUIRED_FIELD = "{0} is a required field";
    public static final String NULL_NOT_ALLOWED = "{0} cannot be null";
    public static final String NEGATIVE_NUMBER_NOT_ALLOWED = "{0} must not be a negative number";
    public static final String PARAMETER_TYPE_MISMATCH =
            "Incorrect value type for parameter \"{0}\" (expected type: {1})";

}
