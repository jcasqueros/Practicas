package com.viewnext.films.persistencelayer.repository.jpa;

import com.viewnext.films.persistencelayer.entity.Serie;
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
class SerieJPARepositoryTest {
    @Autowired
    SerieJPARepository serieJPARepository;
    private Serie serie;

    @BeforeEach
    void setup() {
        serie = new Serie();
        serie.setTitle("Serie 1");
        serie.setReleaseYear(2020);
    }

    @Test
    @DisplayName("Save serie operation")
    void givenSerieObject_whenSaveSerie_thenReturnSavedSerie() {

        Serie savedSerie = serieJPARepository.save(serie);

        assertThat(savedSerie).isNotNull();
        assertThat(savedSerie.getTitle()).isEqualTo(serie.getTitle());
        assertThat(savedSerie.getReleaseYear()).isEqualTo(serie.getReleaseYear());
        assertThat(savedSerie.getId()).isNotZero();

    }

    @Test
    @DisplayName("Find serie by id operation")
    void givenId_whenFindSerieById_thenReturnFoundSerie() {

        Serie savedSerie = serieJPARepository.save(serie);

        Optional<Serie> foundSerie = serieJPARepository.findById(savedSerie.getId());

        assertThat(foundSerie).isPresent();
        assertThat(foundSerie).contains(savedSerie);
    }

    @Test
    @DisplayName("Find all series operation")
    void givenNothing_whenFindAllSeries_thenReturnPageWithAllSeries() {

        Serie savedSerie = serieJPARepository.save(serie);

        Pageable pageable = PageRequest.of(0, 10); // retrieve the first 10 series
        Page<Serie> foundSeriesPage = serieJPARepository.findAll(pageable);

        assertThat(foundSeriesPage).isNotNull();
        assertThat(foundSeriesPage.getContent().get(0)).isEqualTo(savedSerie);

    }

    @Test
    @DisplayName("Delete serie by id operation")
    void givenId_whenDeleteSerieById_thenDeleteSerie() {

        Serie savedSerie = serieJPARepository.save(serie);

        serieJPARepository.deleteById(savedSerie.getId());
        Optional<Serie> foundSerie = serieJPARepository.findById(savedSerie.getId());

        assertThat(foundSerie).isEmpty();

    }
}
