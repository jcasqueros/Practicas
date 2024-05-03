package com.practise.practise.presentation.converters;

import com.practise.practise.business.bo.UserBO;
import com.practise.practise.presentation.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DtoToBoConverter {
    private final ModelMapper modelMapper;

    public UserBO userDtoToUserBo(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserBO.class);
    }
}
