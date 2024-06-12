package com.santander.kafkaprovider.web;

import com.santander.kafkaprovider.model.bo.MessageBO;
import com.santander.kafkaprovider.model.dto.MessageDTO;

import com.santander.kafkaprovider.service.impl.KafkaService;
import com.santander.kafkaprovider.utils.CommonOperation;
import com.santander.kafkaprovider.utils.mapper.dto.MessageDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * The type Kafka controller test.
 */
@ExtendWith(MockitoExtension.class)
class KafkaControllerTest {

    @Mock
    private CommonOperation commonOperation;

    @Mock
    private MessageDTOMapper messageDTOMapper;

    @Mock
    private KafkaService kafkaService;

    @InjectMocks
    private KafkaController kafkaController;

    private MessageDTO messageDTO;
    private MessageBO messageBO;
    private BindingResult bindingResult;

    /**
     * Init.
     */
    @BeforeEach
    void init() {
        messageDTO = MessageDTO.builder().user("user").text("message").primerUser(true).build();

        messageBO = MessageBO.builder().user("user").text("message").primerUser(true).build();

        bindingResult = new BeanPropertyBindingResult(MessageDTO.builder().build(), "messageDTO");

    }

    /**
     * Test create message with valid message.
     */
    @Test
    void testCreateMessage_withValidMessage() {

        when(messageDTOMapper.dtoToBo(messageDTO)).thenReturn(messageBO);

        when(kafkaService.sendMessage(messageBO)).thenReturn(true);

        ResponseEntity<String> response = kafkaController.createMessage(messageDTO, bindingResult);

        verify(kafkaService).sendMessage(messageBO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Message send successfully", response.getBody());
    }

    /**
     * Test create message with invalid message.
     */
    @Test
    void testCreateMessage_withInvalidMessage() {

        bindingResult.rejectValue("user", "error.user.required");
        bindingResult.rejectValue("text", "error.message.required");

        ResponseEntity<String> response = kafkaController.createMessage(messageDTO, bindingResult);

        verify(commonOperation).showErrorModel(kafkaController.logger, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Message not send", response.getBody());
    }
}
