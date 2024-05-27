package com.santander.UserProject.user.util.mapper.bo;

import com.santander.UserProject.user.model.bo.UserBO;
import com.santander.UserProject.user.model.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * The interface Users bo mapper.
 */
@Mapper(componentModel = "spring")
@Component
public interface UserBOMapper {

    /**
     * Bo to entity user.
     *
     * @param userBO
     *         the user bo
     * @return the user
     */
    Users boToEntity(UserBO userBO);

    /**
     * Entity to bo users bo.
     *
     * @param users
     *         the users
     * @return the users bo
     */
    UserBO entityToBo(Users users);


    /**
     * List entityto list bo list.
     *
     * @param usersPage
     *         the users page
     * @return the list
     */
    List<UserBO> listEntitytoListBo(Page<Users> usersPage);
}
