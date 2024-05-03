package com.practise.practise.presentation.controllers;

import com.practise.practise.business.services.UserService;
import com.practise.practise.exceptions.EntityNotFoundException;
import com.practise.practise.exceptions.PresentationException;
import com.practise.practise.exceptions.ServiceException;
import com.practise.practise.exceptions.UpsertException;
import com.practise.practise.persistence.models.User;
import com.practise.practise.presentation.converters.BoToDtoConverter;
import com.practise.practise.presentation.converters.DtoToBoConverter;
import com.practise.practise.presentation.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller of {@link User}
 *
 * @author Manuel Mateos de Torres
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    private final BoToDtoConverter boToDtoConverter;

    private final DtoToBoConverter dtoToBoConverter;

    /**
     * Method for get all users
     *
     * @return ResponseEntity<UserDTO List>
     * @throws ServiceException
     */
    @GetMapping("/findAll")
    public ResponseEntity<List<UserDTO>> findAll() throws ServiceException {
        try {
            return new ResponseEntity<>(userService.findAll().stream().map(boToDtoConverter::userBoToUserDto).toList(),
                    HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for get a user by his id.
     *
     * @param id
     * @return ResponseEntity<UserDTO>
     */
    @GetMapping("/getById/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") String id) throws EntityNotFoundException {
        try {
            return new ResponseEntity<>(boToDtoConverter.userBoToUserDto(userService.findById(id)), HttpStatus.OK);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for create a user
     *
     * @param userDTO
     * @return ResponseEntity<UserDTO>
     */
    @PostMapping("/save")
    public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO) {
        try {
            return new ResponseEntity<>(
                    boToDtoConverter.userBoToUserDto(userService.save(dtoToBoConverter.userDtoToUserBo(userDTO))),
                    HttpStatus.CREATED);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    /**
     * Method for update a user
     *
     * @param userDTO
     * @return ResponseEntity<UserDTO>
     */
    @PutMapping("/update")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        try {
            return new ResponseEntity<>(
                    boToDtoConverter.userBoToUserDto(userService.update(dtoToBoConverter.userDtoToUserBo(userDTO))),
                    HttpStatus.CREATED);
        } catch (ServiceException | UpsertException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestParam("dni") String dni) {
        try {
            userService.delete(dni);
            return new ResponseEntity<>(true, HttpStatus.NO_CONTENT);
        } catch (ServiceException e) {
            throw new PresentationException(e.getLocalizedMessage());
        }
    }
}
