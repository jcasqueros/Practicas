CREATE SEQUENCE "person_seq" START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE "production_seq" START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE "producer_seq" START WITH 1 INCREMENT BY 50;

CREATE TABLE persons(
    id BIGINT,
    name VARCHAR(255) NOT NULL,
    age INTEGER NOT NULL,
    nationality VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE producers(
    id BIGINT,
    name VARCHAR(255) NOT NULL,
    debut INTEGER NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE actors(
    id BIGINT,
    PRIMARY KEY (id)
);

CREATE TABLE directors(
    id BIGINT,
    PRIMARY KEY(id)
);

CREATE TABLE productions(
    id BIGINT,
    title VARCHAR(255) NOT NULL,
    debut INTEGER NOT NULL,
    director_id BIGINT REFERENCES directors(id) ON DELETE CASCADE ON UPDATE CASCADE,
    producer_id BIGINT REFERENCES producers(id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(id)
);

CREATE TABLE films(
    id BIGINT,
    PRIMARY KEY(id)
);

CREATE TABLE series(
    id BIGINT,
    PRIMARY KEY(id)
);

CREATE TABLE production_actors(
    actor_id BIGINT REFERENCES actors(id) ON DELETE CASCADE ON UPDATE CASCADE,
    production_id BIGINT REFERENCES productions(id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY(actor_id, production_id)
);