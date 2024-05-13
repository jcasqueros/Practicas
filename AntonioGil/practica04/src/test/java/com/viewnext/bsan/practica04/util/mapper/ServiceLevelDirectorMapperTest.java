package com.viewnext.bsan.practica04.util.mapper;

import com.viewnex.bsan.practica04.bo.DirectorBo;
import com.viewnex.bsan.practica04.entity.Director;
import com.viewnext.bsan.practica04.bo.DirectorBo;
import com.viewnext.bsan.practica04.entity.Director;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for {@code ServiceLevelDirectorMapper} (implementation provided by MapStruct).
 *
 * @author Antonio Gil
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceLevelDirectorMapperImpl.class})
class ServiceLevelDirectorMapperTest {

    private final ServiceLevelDirectorMapper mapper;

    @Autowired
    public ServiceLevelDirectorMapperTest(ServiceLevelDirectorMapper mapper) {
        this.mapper = mapper;
    }

    @DisplayName("[ServiceLevelDirectorMapper] entityToBo")
    @Test
    void givenEntity_whenEntityToBo_thenMappingIsCorrect() {
        Director entity = Director.builder().id(10L).name("DIRECTOR10").age(70).nationality("GBR").build();

        DirectorBo resultBo = mapper.entityToBo(entity);

        assertEquals(entity.getId(), resultBo.getId());
        assertEquals(entity.getName(), resultBo.getName());
        assertEquals(entity.getAge(), resultBo.getAge());
        assertEquals(entity.getNationality(), resultBo.getNationality());
    }

    @DisplayName("[ServiceLevelDirectorMapper] boToEntity")
    @Test
    void givenBo_whenBoToEntity_thenMappingIsCorrect() {
        DirectorBo bo = DirectorBo.builder().id(4L).name("DIRECTOR4").age(55).nationality("ARG").build();

        Director resultEntity = mapper.boToEntity(bo);

        assertEquals(bo.getId(), resultEntity.getId());
        assertEquals(bo.getName(), resultEntity.getName());
        assertEquals(bo.getAge(), resultEntity.getAge());
        assertEquals(bo.getNationality(), resultEntity.getNationality());
    }

}
