package com.santander.UserProject.user.util.mapper.dto;

import com.santander.UserProject.user.model.bo.UserBO;
import com.santander.UserProject.user.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface UserDTOMapper {

    /**
     * Bo to dto user bo.
     *
     * @param userDTO
     *         the user dto
     * @return the user bo
     */
    @Mapping(target = "id", ignore = true)
    UserBO dtoToBo(UserDTO userDTO);

    /**
     * Dto to bo user dto.
     *
     * @param userBO
     *         the user bo
     * @return the user dto
     */
    UserDTO boToDto(UserBO userBO);

    /**
     * Bos to dtos list.
     *
     * @param userBOS
     *         the user bos
     * @return the list
     */
    List<UserDTO> bosToDtos(List<UserBO> userBOS);

}
