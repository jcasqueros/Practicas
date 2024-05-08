package com.pracs.films.persistence.repositories.criteria;

import com.pracs.films.persistence.models.Director;
import com.pracs.films.persistence.repositories.criteria.impl.DirectorRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ComponentScan("com.pracs.films.persistence.repositories.criteria")
class DirectorRepositoryImplTest {

    @Autowired
    private DirectorRepositoryImpl directorRepository;

    private Director director;

    @BeforeEach
    void setup() {

        director = new Director();
        director.setName("prueba");
        director.setAge(25);
        director.setNationality("Spain");

    }

    @DisplayName("JUnit test for save an director")
    @Test
    void givenDirectorObject_whensaveDirector_thenReturnDirectorObject() {

        Director savedDirector = directorRepository.saveDirector(director);

        assertEquals(director, savedDirector);
    }

    @DisplayName("JUnit test for update a director")
    @Test
    void givenDirectorObject_whenUpdateDirector_thenReturnDirectorObject() {

        directorRepository.saveDirector(director);

        director.setName("update");
        director.setAge(90);
        director.setNationality("update");

        directorRepository.updateDirector(director);

        Optional<Director> savedDirector = directorRepository.findDirectorById(director.getId());

        assertEquals("update", savedDirector.get().getName());
        assertEquals(90, savedDirector.get().getAge());
        assertEquals("update", savedDirector.get().getNationality());
    }

    @DisplayName("JUnit test for get a director by his id")
    @Test
    void givenDirectorId_whenFindDirectorById_thenReturnDirectorObject() {

        Optional<Director> savedDirector = directorRepository.findDirectorById(1L);

        assertEquals(1L, savedDirector.get().getId());
    }

    @DisplayName("JUnit test for get all directors")
    @Test
    void givenNothing_whenFindAllDirector_thenReturnDirectorList() {

        List<Director> savedDirectors = directorRepository.findAllDirector();

        assertEquals(499, savedDirectors.size());
    }

    @DisplayName("JUnit test for delete a director")
    @Test
    void givenDirectorId_whenDeleteDirectorByid_thenDelete() {

        directorRepository.deleteDirectorById(director);

        Optional<Director> foundActor = directorRepository.findDirectorById(director.getId());

        assertThat(foundActor).isEmpty();
    }
}