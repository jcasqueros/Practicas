package com.viewnext.bsan.practica03.util.mapper;

import com.viewnext.bsan.practica03.business.bo.UserBo;
import com.viewnext.bsan.practica03.persistence.entity.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@code ServiceLevelUserMapper} (implementation is provided by MapStruct).
 *
 * @author Antonio Gil
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceLevelUserMapperImpl.class})
class ServiceLevelUserMapperTest {

    private final ServiceLevelUserMapper mapper;

    @Autowired
    public ServiceLevelUserMapperTest(ServiceLevelUserMapper mapper) {
        this.mapper = mapper;
    }

    @DisplayName("[ServiceLevelUserMapper] entityToBo")
    @Test
    void givenEntity_whenEntityToBo_thenMappingIsCorrect() {
        User entity = User.builder().dni("11111111H").name("JOSE").surname("DOMINGUEZ").age(35).build();

        UserBo resultBo = mapper.entityToBo(entity);

        assertEquals(entity.getDni(), resultBo.getDni());
        assertEquals(entity.getName(), resultBo.getName());
        assertEquals(entity.getSurname(), resultBo.getSurname());
        assertEquals(entity.getAge(), resultBo.getAge());
    }

    @DisplayName("[ServiceLevelUserMapper] boToEntity")
    @Test
    void givenBo_whenBoToEntity_thenMappingIsCorrect() {
        UserBo bo = UserBo.builder().dni("22222222J").name("MARIA").surname("GOMEZ").age(40).build();

        User resultEntity = mapper.boToEntity(bo);

        assertEquals(bo.getDni(), resultEntity.getDni());
        assertEquals(bo.getName(), resultEntity.getName());
        assertEquals(bo.getSurname(), resultEntity.getSurname());
        assertEquals(bo.getAge(), resultEntity.getAge());
    }

}
