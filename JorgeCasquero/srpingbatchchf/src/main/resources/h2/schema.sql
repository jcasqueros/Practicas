CREATE TABLE Tramo_Calle (
  codigo_calle BIGINT PRIMARY KEY,
  tipo_via VARCHAR(255),
  nombre_calle VARCHAR(255),
  primer_num_tramo INTEGER,
  ultimo_num_tramo INTEGER,
  barrio INTEGER,
  cod_distrito INTEGER,
  nom_distrito VARCHAR(255)
);