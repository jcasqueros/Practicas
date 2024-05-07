insert into ACTOR (id_actor, nombre, edad, nacionalidad) values (1, 'Peter Dinklage', 32, 'China');
insert into ACTOR (id_actor, nombre, edad, nacionalidad) values (2, 'Andrew Lincoln', 96, 'China');
insert into ACTOR (id_actor, nombre, edad, nacionalidad) values (3, 'Winona Ryder', 1, 'Ecuador');
insert into ACTOR (id_actor, nombre, edad, nacionalidad) values (4, 'Jim Parsons', 8, 'China');
insert into ACTOR (id_actor, nombre, edad, nacionalidad) values (5, 'Bryan Cranston', 66, 'Poland');

insert into director (id_director, nombre, edad, nacionalidad) values (1, 'Prudence', 65, 'United States');
insert into director (id_director, nombre, edad, nacionalidad) values (2, 'Estelle', 96, 'Russia');
insert into director (id_director, nombre, edad, nacionalidad) values (3, 'Marlane', 93, 'Indonesia');
insert into director (id_director, nombre, edad, nacionalidad) values (4, 'Robinetta', 38, 'Indonesia');
insert into director (id_director, nombre, edad, nacionalidad) values (5, 'Aluino', 21, 'Ukraine');

insert into productora (id_productora, nombre, anio_fundacion) values (1, 'Warner Bros', 1919);
insert into productora (id_productora, nombre, anio_fundacion) values (2, 'Paramount Pictures', 1907);
insert into productora (id_productora, nombre, anio_fundacion) values (3, '20th Century Studios', 1952);
insert into productora (id_productora, nombre, anio_fundacion) values (4, 'Universal Pictures', 1953);
insert into productora (id_productora, nombre, anio_fundacion) values (5, 'Sony Pictures', 1937);



insert into SERIE (id_serie, titulo, anio, id_dir, id_pro, id_act) values (1, 'Game of Thrones', 1948, 1, 1, 1);
insert into SERIE (id_serie, titulo, anio, id_dir, id_pro, id_act) values (2, 'The Walking Dead', 1948, 2, 2, 2);
insert into SERIE (id_serie, titulo, anio, id_dir, id_pro, id_act) values (3, 'Stranger Things', 2021, 3, 3, 3);
insert into SERIE (id_serie, titulo, anio, id_dir, id_pro, id_act) values (4, 'The big Bang Theory', 1986, 4, 4, 4);
insert into SERIE (id_serie, titulo, anio, id_dir, id_pro, id_act) values (5, 'Breaking Dad', 1927, 5, 5, 5);


insert into PELICULA (id_pelicula, titulo, anio, id_dir, id_pro, id_act) values (1, 'Delicatessen', 1975, 1, 1, 1);
insert into PELICULA(id_pelicula, titulo, anio, id_dir, id_pro, id_act) values (2, 'Living Ghost, The', 1987, 2, 2, 2);
insert into PELICULA (id_pelicula, titulo, anio, id_dir, id_pro, id_act) values (3, 'Charge of the Light Brigade, The', 2019, 3, 3, 3);
insert into PELICULA (id_pelicula, titulo, anio, id_dir, id_pro, id_act) values (4, 'The Amazing Spider-Man 2', 1958, 4, 4, 4);
insert into PELICULA (id_pelicula, titulo, anio, id_dir, id_pro, id_act) values (5, 'After Innocence', 1993, 5, 5, 5);



INSERT INTO ACTOR_SERIE (ID_ACTOR, ID_SERIE) VALUES
(1, 1),
(2, 1),
(3, 1),
(1, 2),
(2, 2),
(4, 2),
(1, 3),
(3, 3),
(5, 3),
(2, 4),
(3, 4),
(4, 4),
(1, 5),
(4, 5),
(5, 5);
 
INSERT INTO ACTOR_PELICULA(ID_ACTOR, ID_PELICULA) VALUES
(1, 1),
(2, 1),
(3, 1),
(1, 2),
(2, 2),
(4, 2),
(1, 3),
(3, 3),
(5, 3),
(2, 4),
(3, 4),
(4, 4),
(1, 5),
(4, 5),
(5, 5);

