/*
package com.santander.peliculacrud.servicetest.actorservice;

import com.santander.peliculacrud.model.dto.ActorDTO;
import com.santander.peliculacrud.model.entity.Actor;
import com.santander.peliculacrud.service.ActorServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.fail;

*/
/**
 * The type Actor delete service test.
 *//*

@SpringBootTest
public class ActorDeleteServiceTest {

    @Autowired
    private ActorServiceInterface actorService;

    */
/**
     * Test delete actor with valid id.
     *//*

    @Test
    @DisplayName("Delete a actor ")
    public void testDeleteActorWithValidId() {
        Actor actor = new Actor();
        int startSize = -1;
        try {
            startSize  = actorService.getListSize();

            ActorDTO actorDTO = ActorDTO.builder().name("John Doe").age(18).nation("ESP").build();
            actorService.createActor(actorDTO);

            actor = actorService.getLastActor();


            if (actor != null) {
                assertEquals(startSize + 1 , actorService.getListSize(), "There should be same size");
                actorService.deleteActor(actor.getId());

            } else {
                fail("Actor should not be null");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        assertNull(actorService.getActorById(actor.getId()), "Actor should be null after deletion");
        assertEquals(startSize , actorService.getListSize(), "There should be same size");

    }

    */
/**
     * Test delete actor with invalid id.
     *//*

    @Test
    @DisplayName("Delete a actor with invalid id")
    public void testDeleteActorWithInvalidId() {
       int  startSize  = actorService.getListSize();
        Exception exception = assertThrows(Exception.class, () -> actorService.deleteActor((long) -1));
        assertEquals("Actor not found", exception.getMessage());
        assertEquals(startSize , actorService.getListSize(), "There should be same size");


    }

}
*/
