-- ACTORS
INSERT INTO
    ACTORS (
        ACTOR_ID,
        ACTOR_NAME,
        ACTOR_AGE,
        ACTOR_NATIONALITY
    )
VALUES
    (1, 'ACTOR1', 30, 'USA'),
    (2, 'ACTOR2', 50, 'ESP'),
    (3, 'ACTOR3', 45, 'FRA'),
    (4, 'ACTOR4', 25, 'USA'),
    (5, 'ACTOR5', 35, 'ITA');

-- DIRECTORS
INSERT INTO
    DIRECTORS (
        DIRECTOR_ID,
        DIRECTOR_NAME,
        DIRECTOR_AGE,
        DIRECTOR_NATIONALITY
    )
VALUES
    (1, 'DIRECTOR1', 40, 'ITA'),
    (2, 'DIRECTOR2', 60, 'USA'),
    (3, 'DIRECTOR3', 55, 'FRA'),
    (4, 'DIRECTOR4', 35, 'ESP'),
    (5, 'DIRECTOR5', 45, 'USA');

-- PRODUCTION_COMPANIES
INSERT INTO
    PRODUCTION_COMPANIES (
        PRODUCTION_COMPANY_ID,
        PRODUCTION_COMPANY_NAME,
        PRODUCTION_COMPANY_YEAR_FOUNDED
    )
VALUES
    (1, 'PRODUCTION_COMPANY1', 1995),
    (2, 'PRODUCTION_COMPANY2', 1975),
    (3, 'PRODUCTION_COMPANY3', 1980),
    (4, 'PRODUCTION_COMPANY4', 2000),
    (5, 'PRODUCTION_COMPANY5', 1990);

-- FILMS
INSERT INTO
    FILMS (
        FILM_ID,
        FILM_TITLE,
        FILM_YEAR,
        FILM_DIRECTOR_ID,
        FILM_PRODUCTION_COMPANY_ID
    )
VALUES
    (1, 'FILM1', 2005, 5, 4),
    (2, 'FILM2', 2010, 4, 3),
    (3, 'FILM3', 2000, 3, 2),
    (4, 'FILM4', 2015, 2, 1),
    (5, 'FILM5', 1995, 1, 5);

-- ACTOR_FILM_PARTICIPATIONS
INSERT INTO
    ACTOR_FILM_PARTICIPATIONS (FILM_ID, ACTOR_ID)
VALUES
    (1, 2),
    (1, 3),
    (2, 3),
    (2, 4),
    (3, 4),
    (3, 5),
    (4, 5),
    (4, 1),
    (5, 1),
    (5, 2);

-- SHOWS
INSERT INTO
    SHOWS (
        SHOW_ID,
        SHOW_TITLE,
        SHOW_YEAR,
        SHOW_DIRECTOR_ID,
        SHOW_PRODUCTION_COMPANY_ID
    )
VALUES
    (1, 'SHOW1', 2015, 1, 1),
    (2, 'SHOW2', 2020, 2, 2),
    (3, 'SHOW3', 2005, 3, 3),
    (4, 'SHOW4', 2010, 4, 4),
    (5, 'SHOW5', 1995, 5, 5);

-- ACTOR_SHOW_PARTICIPATIONS
INSERT INTO
    ACTOR_SHOW_PARTICIPATIONS (SHOW_ID, ACTOR_ID)
VALUES
    (1, 4),
    (1, 5),
    (2, 5),
    (2, 1),
    (3, 1),
    (3, 2),
    (4, 2),
    (4, 3),
    (5, 3),
    (5, 4);