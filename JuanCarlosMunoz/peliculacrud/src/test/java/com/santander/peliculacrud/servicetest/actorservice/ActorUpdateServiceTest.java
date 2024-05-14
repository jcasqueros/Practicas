/*
package com.santander.peliculacrud.servicetest.actorservice;

import com.santander.peliculacrud.model.dto.ActorDTO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.service.ActorServiceInterface;
import com.santander.peliculacrud.util.TransformObjects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

*/
/**
 * The type Actor update service test.
 *//*

@SpringBootTest
public class ActorUpdateServiceTest {

    @Autowired
    private ActorServiceInterface actorService;
    @Autowired
    private TransformObjects transformObjects;

    private Actor actor;

    */
/**
     * Init.
     *//*

    @BeforeEach
    public void init() {
        try {
            ActorDTO actorDTO = ActorDTO.builder().name("PRV23 Doe").age(18).nation("ESP").build();
            actorService.createActor(actorDTO);
            actor = actorService.getLastActor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    */
/**
     * Test update actor name.
     *//*

    @Test
    @DisplayName("Update with name valid data")
    public void testUpdateActorName() {
        int startSize = -1;
        ActorDTO updatedActor = new ActorDTO();

        try {

            startSize = actorService.getListSize();

            actor.setName("John Doe2");
            ActorDTO actorDTO = transformObjects.actorToActorOut(actor);
            actorService.updateActor(actor.getId(), actorDTO);
            updatedActor = actorService.getActorById(actor.getId());

        } catch (Exception e) {
            e.printStackTrace();

        }

        assertEquals(updatedActor.getName(), "John Doe2", "The name should be John Doe2");
        assertEquals(startSize, actorService.getListSize(), "There should be the same number of actors");

    }

    */
/**
     * Test update actor age.
     *//*

    @Test
    @DisplayName("Update with age valid data")
    public void testUpdateActorAge() {
        int startSize = -1;
        ActorDTO updatedActor = new ActorDTO();
        try {

            startSize = actorService.getListSize();

            actor.setAge(99);
            ActorDTO actorDTO = transformObjects.actorToActorOut(actor);
            actorService.updateActor(actor.getId(), actorDTO);
            updatedActor = actorService.getActorById(actor.getId());

        } catch (Exception e) {
            e.printStackTrace();

        }
        assertEquals(updatedActor.getAge(), 99, "The age should be 99");
        assertEquals(startSize, actorService.getListSize(), "There should be the same number of actors");

    }

    */
/**
     * Test update actor nation.
     *//*

    @Test
    @DisplayName("Update with nation valid data")
    public void testUpdateActorNation() {
        int startSize = -1;
        ActorDTO updatedActor = new ActorDTO();
        try {

            startSize = actorService.getListSize();

            actor.setNation("TEST");
            ActorDTO actorDTO = transformObjects.actorToActorOut(actor);
            actorService.updateActor(actor.getId(), actorDTO);
            updatedActor = actorService.getActorById(actor.getId());

        } catch (Exception e) {
            e.printStackTrace();

        }

        assertEquals(startSize, actorService.getListSize(), "There should be the same number of actors");
        assertEquals(updatedActor.getNation(), "TEST", "The nation should be TEST");

    }

}
*/
