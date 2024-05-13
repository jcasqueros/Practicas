DROP TABLE SERIES_ACTORES IF EXISTS;
DROP TABLE PELICULA_ACTORES IF EXISTS;
DROP TABLE SERIES IF EXISTS;
DROP TABLE PELICULAS IF EXISTS;
DROP TABLE ACTORES IF EXISTS;
DROP TABLE DIRECTORES IF EXISTS;
DROP TABLE PRODUCTORAS IF EXISTS;

CREATE TABLE ACTORES (
ID BIGINT PRIMARY KEY,
NOMBRE VARCHAR(50),
EDAD INT,
NACIONALIDAD VARCHAR(50)
);

CREATE TABLE DIRECTORES (
ID BIGINT PRIMARY KEY,
NOMBRE VARCHAR(50),
EDAD INT,
NACIONALIDAD VARCHAR(50)
);

CREATE TABLE PRODUCTORAS (
ID BIGINT PRIMARY KEY,
NOMBRE VARCHAR(50),
ANHO_FUNDACION INT
);

CREATE TABLE SERIES(
ID BIGINT PRIMARY KEY,
TITULO VARCHAR(50),
ANHO INT,
ID_DIRECTOR BIGINT REFERENCES DIRECTORES(ID),
ID_PRODUCTORA BIGINT REFERENCES PRODUCTORAS(ID)
);

CREATE TABLE PELICULAS(
ID BIGINT PRIMARY KEY,
TITULO VARCHAR(50),
ANHO INT,
ID_DIRECTOR BIGINT REFERENCES DIRECTORES(ID),
ID_PRODUCTORA BIGINT REFERENCES PRODUCTORAS(ID)
);

CREATE TABLE SERIES_ACTORES (
ACTORES_ID BIGINT,
SERIES_ID BIGINT,
FOREIGN KEY (ACTORES_ID) REFERENCES ACTORES(ID),
FOREIGN KEY (SERIES_ID) REFERENCES SERIES(ID),
PRIMARY KEY (ACTORES_ID, SERIES_ID)
);

CREATE TABLE PELICULAS_ACTORES (
ACTORES_ID BIGINT,
PELICULAS_ID BIGINT,
FOREIGN KEY (ACTORES_ID) REFERENCES ACTORES(ID),
FOREIGN KEY (PELICULAS_ID) REFERENCES PELICULAS(ID),
PRIMARY KEY (ACTORES_ID, PELICULAS_ID)
);

