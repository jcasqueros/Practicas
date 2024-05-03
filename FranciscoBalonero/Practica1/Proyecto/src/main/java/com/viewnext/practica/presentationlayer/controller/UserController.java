package com.viewnext.practica.presentationlayer.controller;

import com.viewnext.practica.businesslayer.exception.BusinessException;
import com.viewnext.practica.businesslayer.exception.UserNotFoundException;
import com.viewnext.practica.businesslayer.service.UserService;
import com.viewnext.practica.presentationlayer.dto.UserDTO;
import com.viewnext.practica.presentationlayer.exception.PresentationLayerException;
import com.viewnext.practica.presentationlayer.exception.UserDTONotFoundException;
import com.viewnext.practica.util.Converter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing users.
 *
 * <p>This class defines RESTful endpoints for performing CRUD operations on users. It handles
 * requests related to retrieving all users, retrieving a user by DNI, updating user, deleting users by DNI, and save a
 * user.</p>
 *
 * @author Franciosco Balonero Olivera
 * @see UserService
 * @see Converter
 */
@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final Converter converter;

    @Operation(summary = "Get all users")
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() throws PresentationLayerException {
        try {
            return new ResponseEntity<>(userService.getAll().stream().map(converter::userBoToDTO).toList(),
                    HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserDTONotFoundException("Users not found", e);
        } catch (BusinessException be) {
            throw new PresentationLayerException(be);
        }
    }

    @Operation(summary = "Get user by DNI")
    @GetMapping("/getUser")
    public ResponseEntity<UserDTO> getUserByDni(@RequestParam @Parameter(description = "DNI of the user") String dni)
            throws PresentationLayerException {
        try {
            return new ResponseEntity<>(converter.userBoToDTO(userService.getUserByDni(dni)), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserDTONotFoundException(e);
        } catch (BusinessException be) {
            throw new PresentationLayerException(be);
        }
    }

    @Operation(summary = "Save a new user")
    @PostMapping("/save")
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid UserDTO userDTO) throws PresentationLayerException {
        try {
            return new ResponseEntity<>(converter.userBoToDTO(userService.save(converter.userDTOToBO(userDTO))),
                    HttpStatus.CREATED);
        } catch (BusinessException be) {
            throw new PresentationLayerException("User not created", be);
        }
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam @Parameter(description = "DNI of the user") String dni)
            throws PresentationLayerException {
        try {
            userService.deleteUser(dni);
            return new ResponseEntity<>("The user has been successfully deleted ", HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            throw new UserDTONotFoundException(e);
        } catch (BusinessException e) {
            throw new PresentationLayerException("User not deleted", e);
        }
    }

    @Operation(summary = "Update a user")
    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO) throws PresentationLayerException {
        try {
            return new ResponseEntity<>(converter.userBoToDTO(userService.updateUser(converter.userDTOToBO(userDTO))),
                    HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            throw new UserDTONotFoundException(e);
        } catch (BusinessException e) {
            throw new PresentationLayerException("User not updated", e);
        }
    }
}
