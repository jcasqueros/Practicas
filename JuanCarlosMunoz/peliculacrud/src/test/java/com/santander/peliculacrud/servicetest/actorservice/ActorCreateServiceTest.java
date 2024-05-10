package com.santander.peliculacrud.servicetest.actorservice;

import com.santander.peliculacrud.model.input.Actor;
import com.santander.peliculacrud.model.output.ActorModelController;
import com.santander.peliculacrud.service.ActorServiceInterface;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The type Actor create service test.
 */
@SpringBootTest
public class ActorCreateServiceTest {

    @Autowired
    private ActorServiceInterface actorService;

    /**
     * Test create actor valid data.
     */
    @Test
    @DisplayName("Create a new actor with valid data")
    public void testCreateActorValidData() {
        int startSize = -1;
        try {
            startSize = actorService.getListSize();

            ActorModelController actorModelController = ActorModelController.builder().name("John Doe").age(18).nation("ESP").build();
            actorService.createActor(actorModelController);

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(startSize + 1, actorService.getListSize(), "There should be only one actor");
        Actor actor = actorService.getLastActor();
        assertEquals(actor.getName(), "John Doe");
        assertEquals(actor.getAge(), 18);
        assertEquals(actor.getNation(), "ESP");

    }

    /**
     * Test create actor invalid data age.
     */
    @Test
    @DisplayName("Create a new actor with invalid data (age < 18)")
    public void testCreateActorInvalidDataAge() {
        ActorModelController actorModelController = ActorModelController.builder().name("John Doe").age(17).nation("ESP").build();
        int startSize = -1;

        try {

            startSize = actorService.getListSize();
            actorService.createActor(actorModelController);

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(startSize, actorService.getListSize(), "There should be error for invalid data (age < 18) ");

    }

    /**
     * Test create actor invalid data name.
     */
    @Test
    @DisplayName("Create a new actor with invalid data (name is null)")
    public void testCreateActorInvalidDataName() {
        ActorModelController actorModelController = ActorModelController.builder().age(18).nation("ESP").build();
        int startSize = -1;
        try {
            startSize = actorService.getListSize();

            actorService.createActor(actorModelController);

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(startSize, actorService.getListSize(), "There should be error for invalid data name");

    }

    /**
     * Test create actor invalid data nation.
     */
    @Test
    @DisplayName("Create a new actor with invalid data (nation is null)")
    public void testCreateActorInvalidDataNation() {
        ActorModelController actorModelController = ActorModelController.builder().name("John Doe").age(18).build();
        int startSize = -1;
            startSize = actorService.getListSize();

        Exception exception = assertThrows(Exception.class, () ->  actorService.createActor(actorModelController));

        assertEquals(startSize, actorService.getListSize(), "There should be error for invalid data nation");

    }
}
