INSERT INTO ACTORES (ID, NOMBRE, EDAD, NACIONALIDAD) VALUES
(1, 'Tom Hanks', 65, 'Estadounidense'),
(2, 'Leonardo DiCaprio', 47, 'Estadounidense'),
(3, 'Meryl Streep', 73, 'Estadounidense'),
(4, 'Denzel Washington', 68, 'Estadounidense'),
(5, 'Julia Roberts', 54, 'Estadounidense');

INSERT INTO DIRECTORES (ID, NOMBRE, EDAD, NACIONALIDAD) VALUES
(1, 'Martin Scorsese', 79, 'Estadounidense'),
(2, 'Quentin Tarantino', 59, 'Estadounidense'),
(3, 'Steven Spielberg', 75, 'Estadounidense'),
(4, 'Christopher Nolan', 51, 'Británico'),
(5, 'James Cameron', 68, 'Canadiense');

INSERT INTO PRODUCTORAS (ID, NOMBRE, ANHO_FUNDACION) VALUES
(1, 'Warner Bros.', 1903),
(2, 'Universal Pictures', 1912),
(3, 'Paramount Pictures', 1912),
(4, '20th Century Studios', 1915),
(5, 'Sony Pictures', 1924);

INSERT INTO SERIES (ID, TITULO, ANHO, ID_DIRECTOR, ID_PRODUCTORA) VALUES
(1, 'Game of Thrones', 2011, 1, 1),
(2, 'The Walking Dead', 2010, 2, 2),
(3, 'Stranger Things', 2016, 3, 3),
(4, 'The Big Bang Theory', 2007, 4, 4),
(5, 'Breaking Bad', 2008, 5, 5);

INSERT INTO PELICULAS (ID, TITULO, ANHO, ID_DIRECTOR, ID_PRODUCTORA) VALUES
(1, 'Titanic', 1997, 5, 5),
(2, 'The Revenant', 2015, 2, 2),
(3, 'Inception', 2010, 4, 4),
(4, 'The Shawshank Redemption', 1994, 1, 1),
(5, 'Forrest Gump', 1994, 3, 3);

INSERT INTO SERIES_ACTORES (ACTORES_ID, SERIES_ID) VALUES
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

INSERT INTO PELICULAS_ACTORES (ACTORES_ID, PELICULAS_ID) VALUES
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
