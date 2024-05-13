package com.viewnext.films.persistencelayer.repository.jpa;

import com.viewnext.films.persistencelayer.entity.Director;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = false)
@ComponentScan(basePackages = "com.viewnext.films.persistencelayer.repository.jpa")
@Sql(scripts = "/no-data.sql")
class DirectorJPARepositoryTest {
    @Autowired
    DirectorJPARepository directorJPARepository;
    private Director director;

    @BeforeEach
    void setup() {
        director = new Director();
        director.setAge(18);
        director.setName("Jhon");
        director.setNationality("spain");
    }

    @Test
    @DisplayName("Save director operation")
    void givenDirectorObject_whenSaveDirector_thenReturnSavedDirector() {

        Director savedDirector = directorJPARepository.save(director);

        assertThat(savedDirector).isNotNull();
        assertThat(savedDirector.getName()).isEqualTo(director.getName());
        assertThat(savedDirector.getAge()).isEqualTo(director.getAge());
        assertThat(savedDirector.getNationality()).isEqualTo(director.getNationality());
        assertThat(savedDirector.getId()).isNotNull();

    }

    @Test
    @DisplayName("Find director by id operation")
    void givenId_whenFindDirectorById_thenReturnFoundDirector() {

        Director savedDirector = directorJPARepository.save(director);

        Optional<Director> foundDirector = directorJPARepository.findById(savedDirector.getId());

        assertThat(foundDirector).isPresent();
        assertThat(foundDirector).contains(savedDirector);
    }

    @Test
    @DisplayName("Find all directors operation")
    void givenNothing_whenFindAllDirectors_thenReturnPageWithAllDirectors() {

        Director savedDirector = directorJPARepository.save(director);

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 directors
        Page<Director> foundDirectorsPage = directorJPARepository.findAll(pageable);

        assertThat(foundDirectorsPage).isNotNull();
        assertThat(foundDirectorsPage.getContent().get(0)).isEqualTo(savedDirector);

    }

    @Test
    @DisplayName("Delete director by id operation")
    void givenId_whenDeleteDirectorById_thenDeleteDirector() {

        Director savedDirector = directorJPARepository.save(director);

        directorJPARepository.deleteById(savedDirector.getId());
        Optional<Director> foundDirector = directorJPARepository.findById(savedDirector.getId());

        assertThat(foundDirector).isEmpty();

    }
}
