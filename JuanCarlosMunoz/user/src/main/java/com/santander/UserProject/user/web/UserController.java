package com.santander.UserProject.user.web;

import com.santander.UserProject.user.model.bo.UserBO;
import com.santander.UserProject.user.model.dto.UserDTO;
import com.santander.UserProject.user.service.UserServiceInterface;
import com.santander.UserProject.user.util.CommonOperation;
import com.santander.UserProject.user.util.exception.GenericException;
import com.santander.UserProject.user.util.mapper.dto.UserDTOMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Users controller.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceInterface userService;
    private final CommonOperation commonOperation;
    private final UserDTOMapper userDTOMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Instantiates a new Users controller.
     *
     * @param userService
     *         the user service
     * @param commonOperation
     *         the common operation
     * @param userDTOMapper
     *         the user dto mapper
     */
    @Autowired
    public UserController(UserServiceInterface userService, CommonOperation commonOperation,
            UserDTOMapper userDTOMapper) {
        this.userService = userService;
        this.commonOperation = commonOperation;
        this.userDTOMapper = userDTOMapper;
    }

    /**
     * Create user response entity.
     *
     * @param user
     *         the user
     * @param bindingResult
     *         the binding result
     * @return the response entity
     * @throws GenericException
     *         the generic exception
     */
    @ApiOperation(value = "Create a new user", notes = "Create a new user with the provided information")
    @ApiResponses({ @ApiResponse(code = 201, message = "Users created successfulnesses"),
            @ApiResponse(code = 400, message = "Invalid request") })
    @PostMapping
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO user, BindingResult bindingResult)
            throws GenericException {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "Users not created";

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            UserBO userBO = userDTOMapper.dtoToBo(user);
            if (userService.createUser(userBO) != null) {
                message = "Users created successfully";
                status = HttpStatus.CREATED;

            }

        }
        return new ResponseEntity<>(message, status);

    }

    /**
     * Update user response entity.
     *
     * @param id
     *         the id
     * @param updatedUser
     *         the updated user
     * @param bindingResult
     *         the binding result
     * @return the response entity
     * @throws GenericException
     *         the generic exception
     */
    @PutMapping("/")
    public ResponseEntity<String> updateUser(@RequestParam @NotNull Long id, @Valid @RequestBody UserDTO updatedUser,
            BindingResult bindingResult) throws GenericException {
        String message = "Users not update";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (bindingResult.hasErrors()) {

            commonOperation.showErrorModel(logger, bindingResult);

        } else {
            UserBO userBO = userDTOMapper.dtoToBo(updatedUser);
            if (userService.updateUser(id, userBO)) {
                message = "Users updated successfully";
                status = HttpStatus.OK;

            }
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Delete user response entity.
     *
     * @param id
     *         the id
     * @return the response entity
     * @throws GenericException
     *         the generic exception
     */
    @DeleteMapping("/")
    public ResponseEntity<String> deleteUser(@RequestParam @NotNull Long id) throws GenericException {
        String message = "Users not delete";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if (userService.deleteUser(id)) {
            message = "Users delete";
            status = HttpStatus.NO_CONTENT;
        }

        return new ResponseEntity<>(message, status);
    }

    /**
     * Gets user by id.
     *
     * @param id
     *         the id
     * @return the user by id
     */
    @GetMapping("/")
    public ResponseEntity<UserDTO> getUserById(@RequestParam @NotNull Long id) {
        UserBO userBO = userService.getUserById(id);
        if (userBO == null) {
            logger.error("Users not found");
            return ResponseEntity.notFound().build();
        }
        UserDTO userDTO = userDTOMapper.boToDto(userBO);
        return ResponseEntity.ok(userDTO);
    }

    /**
     * Gets users by name.
     *
     * @param name
     *         the name
     * @param age
     *         the age
     * @param page
     *         the page
     * @return the users by name
     */
    @GetMapping("/by-name-age/")
    public ResponseEntity<List<UserDTO>> getUsersByNameAge(@RequestParam String name, @RequestParam int age,
            @RequestParam(defaultValue = "0") int page) {
        List<UserBO> userBOs = userService.getUserByNameAge(name, age, page);
        List<UserDTO> userDTOS = new ArrayList<>();
        userDTOS = userDTOMapper.bosToDtos(userBOs);
        return ResponseEntity.ok(userDTOS);
    }

}

