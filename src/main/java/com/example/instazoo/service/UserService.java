package com.example.instazoo.service;

import com.example.instazoo.dto.UserDTO;
import com.example.instazoo.entity.User;
import com.example.instazoo.entity.enums.ERole;
import com.example.instazoo.exseptions.UserExistException;
import com.example.instazoo.payload.request.SignUpRequest;
import com.example.instazoo.repository.UserRepository;
import com.example.instazoo.service.GetUserByPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Slf4j
@Service
public class UserService extends GetUserByPrincipal {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    public void createUser(SignUpRequest userIn) {
        User user = new User();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastName(userIn.getLastname());
        user.setUserName(userIn.getUsername());
        user.setPassword(encoder.encode(userIn.getPassword()));
        user.getRole().add(ERole.ROLE_USER); // user will have default one role

        try {
            log.info("Saving user {}", userIn.getEmail());
            userRepository.save(user);
        } catch (Exception ex) {
            log.error("Error during registration {}", ex.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " alredy exist. Please check credentials");
        }
    }

    public User updateUser(UserDTO userDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        user.setName(userDTO.getFirstname());
        user.setLastName(userDTO.getLastname());
        user.setBio(userDTO.getBio());

        return userRepository.save(user);
    }

    public User getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
