package com.viewnex.bsan.practica04.sampledata;

import com.viewnex.bsan.practica04.entity.Director;

import java.util.List;

public class DirectorSampleData {
    public static final List<Director> SAMPLE_DIRECTORS = List.of(
            Director.builder().id(1L).name("DIRECTOR1").age(40).nationality("ITA").build(),
            Director.builder().id(2L).name("DIRECTOR2").age(60).nationality("USA").build(),
            Director.builder().id(3L).name("DIRECTOR3").age(55).nationality("FRA").build(),
            Director.builder().id(4L).name("DIRECTOR4").age(35).nationality("ESP").build(),
            Director.builder().id(5L).name("DIRECTOR5").age(45).nationality("USA").build()
    );
}
