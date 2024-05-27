package com.viewnext.bsan.practica04.sampledata;

import com.viewnext.bsan.practica04.persistence.entity.Actor;

import java.util.List;

/**
 * Sample data for actors. Useful for unit tests, especially if they involve mocking.
 *
 * @author Antonio Gil
 */
public class ActorSampleData {
    public static final List<Actor> SAMPLE_ACTORS = List.of(
            Actor.builder().id(1L).name("ACTOR1").age(30).nationality("USA").build(),
            Actor.builder().id(2L).name("ACTOR2").age(50).nationality("ESP").build(),
            Actor.builder().id(3L).name("ACTOR3").age(45).nationality("FRA").build(),
            Actor.builder().id(4L).name("ACTOR4").age(25).nationality("USA").build(),
            Actor.builder().id(5L).name("ACTOR5").age(35).nationality("ITA").build()
    );
}
