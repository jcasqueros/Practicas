package com.viewnext.bsan.practica04.repository.custom.impl;

import com.viewnext.bsan.practica04.config.CrudPeliculasAppTestConfig;
import com.viewnext.bsan.practica04.entity.Director;
import com.viewnext.bsan.practica04.sampledata.DirectorSampleData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code CustomActorRepositoryImpl}.
 *
 * @author Antonio Gil
 */
@DataJpaTest
@Import(CrudPeliculasAppTestConfig.class)
class CustomDirectorRepositoryImplTest {

    private static final List<Director> SAMPLE_DIRECTORS = DirectorSampleData.SAMPLE_DIRECTORS;

    private final CustomDirectorRepositoryImpl repository;
    private final TestEntityManager testEntityManager;

    @Autowired
    public CustomDirectorRepositoryImplTest(CustomDirectorRepositoryImpl repository,
                                            TestEntityManager testEntityManager) {
        this.repository = repository;
        this.testEntityManager = testEntityManager;
    }

}
