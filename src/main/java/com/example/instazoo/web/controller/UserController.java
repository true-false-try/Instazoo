package com.example.instazoo.web.controller;

import com.example.instazoo.dto.UserDTO;
import com.example.instazoo.entity.User;
import com.example.instazoo.mapping.UserMapper;
import com.example.instazoo.service.UserService;
import com.example.instazoo.validations.ResponseErrorValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;

    @GetMapping
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {
        User user = userService.getCurrentUser(principal);
        UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") Long userId) {
        User user = userService.getUserById(userId);
        UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO, BindingResult result, Principal principal) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(result);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        User user = userService.updateUser(userDTO, principal);
        UserDTO userUpdated =  UserMapper.INSTANCE.userToUserDTO(user);

        return new ResponseEntity<>(userUpdated, HttpStatus.OK);

    }
}
