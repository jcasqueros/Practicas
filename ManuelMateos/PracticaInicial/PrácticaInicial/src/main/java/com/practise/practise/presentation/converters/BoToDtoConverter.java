package com.practise.practise.presentation.converters;

import com.practise.practise.business.bo.UserBO;
import com.practise.practise.presentation.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoToDtoConverter {
    private final ModelMapper modelMapper;

    public UserDTO userBoToUserDto(UserBO userBO) {
        return modelMapper.map(userBO, UserDTO.class);
    }
}
