CREATE TABLE actors(
    actor_id BIGINT,
    actor_name VARCHAR(60),
    actor_age INT,
    actor_nacionality VARCHAR(60),
    PRIMARY KEY(actor_id)
);

CREATE TABLE directors(
    director_id BIGINT,
    director_name VARCHAR(100),
    director_age INT,
    director_nacionality VARCHAR(60),
    PRIMARY KEY(director_id)
);

CREATE TABLE producers(
    producer_id BIGINT,
    producer_name VARCHAR(60),
    producer_year INT,
    PRIMARY KEY(producer_id)
);

CREATE TABLE series(
    serie_id BIGINT,
    serie_title VARCHAR(60),
    serie_year INT,
    serie_director BIGINT REFERENCES directors(director_id),
    serie_producer BIGINT REFERENCES producers(producer_id),
    PRIMARY KEY(serie_id)
);

CREATE TABLE films(
    film_id BIGINT,
    film_title VARCHAR(60),
    film_year INT,
    film_director BIGINT REFERENCES directors(director_id),
    film_producer BIGINT REFERENCES producers(producer_id),
    PRIMARY KEY(film_id)
);

CREATE TABLE actors_series(
    actor_id BIGINT REFERENCES actors(actor_id),
    serie_id BIGINT REFERENCES series(serie_id),
    PRIMARY KEY(actor_id, serie_id)
);

CREATE TABLE actors_films(
    actor_id BIGINT REFERENCES actors(actor_id),
    film_id BIGINT REFERENCES films(film_id),
    PRIMARY KEY(actor_id, film_id)
);