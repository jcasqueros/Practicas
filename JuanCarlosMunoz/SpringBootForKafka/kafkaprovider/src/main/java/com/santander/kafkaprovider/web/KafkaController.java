package com.santander.kafkaprovider.web;

import com.santander.kafkaprovider.model.bo.MessageBO;
import com.santander.kafkaprovider.model.dto.MessageDTO;
import com.santander.kafkaprovider.service.impl.KafkaService;
import com.santander.kafkaprovider.service.KafkaServiceImpl;
import com.santander.kafkaprovider.utils.CommonOperation;
import com.santander.kafkaprovider.utils.mapper.dto.MessageDTOMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Kafka controller.
 */
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final CommonOperation commonOperation;
    final Logger logger = LoggerFactory.getLogger(KafkaController.class);
    private final MessageDTOMapper messageDTOMapper;
    private final KafkaServiceImpl kafkaService;

    /**
     * Instantiates a new Kafka controller.
     *
     * @param commonOperation
     *         the common operation
     * @param messageDTOMapper
     *         the message dto mapper
     * @param kafkaService
     *         the kafka service
     */
    @Autowired
    public KafkaController(CommonOperation commonOperation, MessageDTOMapper messageDTOMapper,
            KafkaService kafkaService) {

        this.commonOperation = commonOperation;
        this.messageDTOMapper = messageDTOMapper;
        this.kafkaService = kafkaService;
    }

    /**
     * Create message response entity.
     *
     * @param message
     *         the message
     * @param bindingResult
     *         the binding result
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<String> createMessage(@Valid @RequestBody MessageDTO message, BindingResult bindingResult) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String messageError = "Message not send";

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            MessageBO messageBO = messageDTOMapper.dtoToBo(message);
            if (kafkaService.sendMessage(messageBO)) {
                messageError = "Message send successfully";
                status = HttpStatus.CREATED;

            }

        }
        return new ResponseEntity<>(messageError, status);

    }
}
