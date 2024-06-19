La ejecución tiene 3 tipos distintos de ejecución:
    - Se introduce por parámetro: jobExportarCSV -> Exporta de la Base de datos los distritos y tramos.
    - Se introduce por parámetro: jobImportarDB, Como segundo parámetro se intruduce filtro por distrito (ESTE,OESTE, NORTE, SUR, ALL) ->  Importa todos los distritos y tramos del fichero tramo_calle_BarrioDismuni.csv.
    - Se introduce por parámetro: jobParaleloWrite -> Convierte los csv de ficherosUsuraios a txt en ficherosFinales. (En modo paralelo)
    - Por defecto:  Importa todos los distritos y tramos del fichero tramo_calle_BarrioDismuniOneMillon.csv.(En modo multihilo)
