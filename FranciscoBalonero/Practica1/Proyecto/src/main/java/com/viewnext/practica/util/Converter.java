package com.viewnext.practica.util;

import com.viewnext.practica.businesslayer.bo.UserBO;
import com.viewnext.practica.persistencelayer.entity.User;
import com.viewnext.practica.presentationlayer.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Converter class for mapping business objects, entities and data transfer objects.
 *
 * <p>This class uses the ModelMapper library for automatic mapping.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * Converter converter = new Converter();
 * User userEntity = converter.userBOToEntity(userBO);
 * }
 * </pre>
 *
 * @author Franciosco Balonero Olivera
 * @see ModelMapper
 */
@Component
public class Converter {
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts a UserBO to a User entity.
     *
     * @param userBO
     *         {@link UserBO} The UserBO
     * @return {@link User} The corresponding User entity
     */
    public User userBOToEntity(UserBO userBO) {
        return modelMapper.map(userBO, User.class);
    }

    /**
     * Converts a User entity to a UserBO.
     *
     * @param user
     *         The user entity
     * @return The corresponding UserBO
     */
    public UserBO userEntityToBO(User user) {
        return modelMapper.map(user, UserBO.class);
    }

    /**
     * Converts a UserBO to a UserDTO.
     *
     * @param userBO
     *         The {@link UserBO} object to convert
     * @return The {@link UserDTO} object representing the converted user
     */
    public UserDTO userBoToDTO(UserBO userBO) {
        return modelMapper.map(userBO, UserDTO.class);
    }

    /**
     * Converts a UserDTO to a UserBO.
     *
     * @param userDTO
     *         The {@link UserDTO} object to convert
     * @return The {@link UserBO} object representing the converted user
     */
    public UserBO userDTOToBO(UserDTO userDTO) {
        return modelMapper.map(userDTO, UserBO.class);
    }
}
