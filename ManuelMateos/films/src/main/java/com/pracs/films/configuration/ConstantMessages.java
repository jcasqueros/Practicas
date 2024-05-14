package com.pracs.films.configuration;

import lombok.Getter;

@Getter
public class ConstantMessages {

    private static final String NOACTORS = "No actors";

    private static final String NODIRECTORS = "No directors";

    private static final String NOPRODUCERS = "No producers";

    private static final String NOFILMS = "No films";

    private static final String ERRORPERSON = "Person not found";

    private static final String ERORRPRODUCER = "Producer not found";

    private static final String ERORRPRODUCTION = "Production not found";

    private static final String ERRORSERVICE = "Error in service layer";

    public String noActors() {
        return NOACTORS;
    }

    public String noDirectors() {
        return NODIRECTORS;
    }

    public String noProducers() {
        return NOPRODUCERS;
    }

    public String noFilms() {
        return NOFILMS;
    }

    public String errorPerson() {
        return ERRORPERSON;
    }

    public String errorProducer() {
        return ERORRPRODUCER;
    }

    public String errorProduction() {
        return ERORRPRODUCTION;
    }

    public String errorService() {
        return ERRORSERVICE;
    }
}
