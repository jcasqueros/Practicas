package com.viewnext.bsan.practica04.persistence.repository.custom.impl;

import com.viewnext.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnext.bsan.practica04.persistence.entity.Film;
import com.viewnext.bsan.practica04.sampledata.FilmSampleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

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
