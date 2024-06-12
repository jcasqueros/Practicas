package com.santander.kafkaprovider.utils.mapper.bo;

import com.santander.kafkaprovider.model.bo.MessageBO;
import com.santander.kafkaprovider.model.entity.Message;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * The interface Message bo mapper.
 */
@Mapper(componentModel = "spring")
@Component
public interface MessageBOMapper {

    /**
     * Bo to entity message.
     *
     * @param messageBO
     *         the message bo
     * @return the message
     */
    Message boToEntity(MessageBO messageBO);

}
