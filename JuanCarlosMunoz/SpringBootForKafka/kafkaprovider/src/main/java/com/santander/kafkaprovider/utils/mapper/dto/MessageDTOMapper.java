package com.santander.kafkaprovider.utils.mapper.dto;

import com.santander.kafkaprovider.model.bo.MessageBO;
import com.santander.kafkaprovider.model.dto.MessageDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * The interface Message dto mapper.
 */
@Mapper(componentModel = "spring")
@Component
public interface MessageDTOMapper {

    /**
     * Bo to dto message bo.
     *
     * @param messageDTO
     *         the message dto
     * @return the message bo
     */
    MessageBO dtoToBo(MessageDTO messageDTO);

    /**
     * Dto to bo message dto.
     *
     * @param messageBO
     *         the message bo
     * @return the message dto
     */
    MessageDTO boToDto(MessageBO messageBO);

}
