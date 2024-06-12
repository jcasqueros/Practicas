package com.santander.kafkaprovider.util.mapper.dto;


import com.santander.kafkaprovider.model.bo.MessageBO;
import com.santander.kafkaprovider.model.dto.MessageDTO;

import com.santander.kafkaprovider.utils.mapper.dto.MessageDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The type Message bo mapper test.
 */
@ExtendWith(MockitoExtension.class)
class MessageDTOMapperTest {

    @InjectMocks
    private MessageDTOMapper messageDTOMapper = Mappers.getMapper(MessageDTOMapper.class);
    private MessageDTO messageDTO;

    @BeforeEach
    void init() {
        messageDTO = MessageDTO.builder().user("user").text("message").primerUser(true).build();

    }

    /**
     * Test dto to bo.
     */
    @Test
    @DisplayName("Test message mapper dto to bo")
    void testDtoToBo() {

        MessageBO messageBOreturn = messageDTOMapper.dtoToBo(messageDTO);

        assertNotNull(messageBOreturn);
        assertEquals(messageBOreturn.getPrimerUser(), messageDTO.getPrimerUser());
        assertEquals(messageBOreturn.getText(), messageDTO.getText());
        assertEquals(messageBOreturn.getUser(), messageDTO.getUser());
    }

}
