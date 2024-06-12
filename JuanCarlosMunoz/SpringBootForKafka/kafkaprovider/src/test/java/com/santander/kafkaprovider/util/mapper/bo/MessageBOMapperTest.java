package com.santander.kafkaprovider.util.mapper.bo;

import com.santander.kafkaprovider.model.bo.MessageBO;
import com.santander.kafkaprovider.model.entity.Message;
import com.santander.kafkaprovider.utils.mapper.bo.MessageBOMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Message bo mapper test.
 */
@ExtendWith(MockitoExtension.class)
class MessageBOMapperTest {

    @InjectMocks
    private MessageBOMapper messageBOMapper = Mappers.getMapper(MessageBOMapper.class);
    private MessageBO messageBO;

    @BeforeEach
    void init() {
        messageBO = MessageBO.builder().user("user").text("message").primerUser(true).build();


    }
    
    /**
     * Test dto to bo.
     */
    @Test
    @DisplayName("Test message mapper dto to bo")
    void testDtoToBo() {

        Message messageBOreturn = messageBOMapper.boToEntity(messageBO);

        assertNotNull(messageBO);
        assertEquals(messageBOreturn.getPrimerUser(), messageBO.getPrimerUser());
        assertEquals(messageBOreturn.getText(), messageBO.getText());
        assertEquals(messageBOreturn.getUser(), messageBO.getUser());
    }



}
