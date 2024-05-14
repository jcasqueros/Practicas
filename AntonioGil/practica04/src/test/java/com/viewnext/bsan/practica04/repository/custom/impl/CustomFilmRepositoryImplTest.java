package com.viewnext.bsan.practica04.repository.custom.impl;

import com.viewnext.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnext.bsan.practica04.entity.Actor;
import com.viewnext.bsan.practica04.entity.Director;
import com.viewnext.bsan.practica04.entity.Film;
import com.viewnext.bsan.practica04.entity.ProductionCompany;
import com.viewnext.bsan.practica04.sampledata.ActorSampleData;
import com.viewnext.bsan.practica04.sampledata.DirectorSampleData;
import com.viewnext.bsan.practica04.sampledata.FilmSampleData;
import com.viewnext.bsan.practica04.sampledata.ProductionCompanySampleData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code CustomActorRepositoryImpl}.
 *
 * @author Antonio Gil
 */
@DataJpaTest
@Import(CrudPeliculasAppTestConfig.class)
class CustomFilmRepositoryImplTest {

    private static final List<Film> SAMPLE_FILMS = FilmSampleData.SAMPLE_FILMS;

    private final CustomFilmRepositoryImpl repository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public CustomFilmRepositoryImplTest(CustomFilmRepositoryImpl repository, TestEntityManager testEntityManager) {
        this.repository = repository;
        this.testEntityManager = testEntityManager;
    }

    private void persistFilmWithNestedEntities(Film film) {
        testEntityManager.persist(film.getDirector());
        testEntityManager.persist(film.getProductionCompany());
        film.getActors().forEach(testEntityManager::persist);
        testEntityManager.persist(film);
    }

}
