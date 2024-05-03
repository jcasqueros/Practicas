package com.practise.practise.business.converters;

import com.practise.practise.business.bo.UserBO;
import com.practise.practise.persistence.models.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * Component to map model objects to bo objects
 *
 * @author Manuel Mateos de Torres
 * @see {@link ModelMapper}
 */
@Component
@RequiredArgsConstructor
public class ModelToBoConverter {

    private final ModelMapper modelMapper;

    public UserBO userModelToUserBo(User user) {
        return modelMapper.map(user, UserBO.class);
    }
}
