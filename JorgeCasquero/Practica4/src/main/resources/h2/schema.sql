CREATE TABLE ACTOR(
	ID_ACTOR BIGINT PRIMARY KEY,
	NOMBRE VARCHAR(150),
	EDAD INTEGER,
	NACIONALIDAD VARCHAR(150)
);

CREATE TABLE DIRECTOR(
	ID_DIRECTOR BIGINT PRIMARY KEY,
	NOMBRE VARCHAR(150),
	EDAD INTEGER,
	NACIONALIDAD VARCHAR(150)
);

CREATE TABLE PRODUCTORA(
	ID_PRODUCTORA BIGINT PRIMARY KEY,
	NOMBRE VARCHAR(150),
	ANIO_FUNDACION INTEGER
);


CREATE TABLE SERIE(
	ID_SERIE BIGINT PRIMARY KEY,
	TITULO VARCHAR(150),
	ANIO INTEGER,
	ID_ACT BIGINT REFERENCES ACTOR(ID_ACTOR)on delete cascade on update cascade,
	ID_DIR BIGINT REFERENCES DIRECTOR(ID_DIRECTOR)on delete cascade on update cascade,
	ID_PRO BIGINT REFERENCES PRODUCTORA(ID_PRODUCTORA)on delete cascade on update cascade
);

CREATE TABLE PELICULA(
	ID_PELICULA BIGINT PRIMARY KEY,
	TITULO VARCHAR(150),
	ANIO INTEGER,
	ID_DIR BIGINT REFERENCES DIRECTOR(ID_DIRECTOR)on delete cascade on update cascade,
	ID_PRO BIGINT REFERENCES PRODUCTORA(ID_PRODUCTORA)on delete cascade on update cascade,
	ID_ACT BIGINT REFERENCES ACTOR(ID_ACTOR)on delete cascade on update cascade
);

CREATE TABLE actor_serie (
  ID_ACTOR BIGINT,
  ID_SERIE BIGINT,
  PRIMARY KEY (ID_ACTOR, ID_SERIE),
  FOREIGN KEY (ID_ACTOR) REFERENCES ACTOR (ID_ACTOR)on delete cascade on update cascade,
  FOREIGN KEY (ID_SERIE) REFERENCES SERIE (ID_SERIE)on delete cascade on update cascade
);

CREATE TABLE actor_pelicula (
  ID_ACTOR BIGINT,
  ID_PELICULA BIGINT,
  PRIMARY KEY (id_actor, id_pelicula),
  FOREIGN KEY (ID_ACTOR) REFERENCES ACTOR (ID_ACTOR)on delete cascade on update cascade,
  FOREIGN KEY (ID_PELICULA) REFERENCES PELICULA (ID_PELICULA)on delete cascade on update cascade
);