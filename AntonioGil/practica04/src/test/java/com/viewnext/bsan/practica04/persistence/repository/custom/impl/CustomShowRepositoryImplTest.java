package com.viewnext.bsan.practica04.persistence.repository.custom.impl;

import com.viewnext.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnext.bsan.practica04.persistence.entity.Show;
import com.viewnext.bsan.practica04.sampledata.ShowSampleData;
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
class CustomShowRepositoryImplTest {

    private static final List<Show> SAMPLE_SHOWS = ShowSampleData.SAMPLE_SHOWS;

    private final CustomShowRepositoryImpl repository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public CustomShowRepositoryImplTest(CustomShowRepositoryImpl repository, TestEntityManager testEntityManager) {
        this.repository = repository;
        this.testEntityManager = testEntityManager;
    }

    private void persistShowWithNestedEntities(Show show) {
        testEntityManager.persist(show.getDirector());
        testEntityManager.persist(show.getProductionCompany());
        show.getActors().forEach(testEntityManager::persist);
        testEntityManager.persist(show);
    }

}
