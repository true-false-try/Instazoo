package com.example.instazoo.service;

import com.example.instazoo.entity.User;
import com.example.instazoo.entity.enums.ERole;
import com.example.instazoo.exseptions.UserExistException;
import com.example.instazoo.payload.request.SignUpRequest;
import com.example.instazoo.repository.UserRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public User createUser(SignUpRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastName(userIn.getLastname());
        user.setUserName(userIn.getUsername());
        user.setPassword(encoder.encode(userIn.getPassword()));
        user.getRole().add(ERole.ROLE_USER); // user will have default one role

        try {
            log.info("Saving user {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception ex) {
            log.error("Error during registration {}", ex.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " alredy exist. Please check credentials");
        }
    }
}
