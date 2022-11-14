package com.example.instazoo.service;

import com.example.instazoo.entity.User;
import com.example.instazoo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
@AllArgsConstructor
public abstract class GetUserByPrincipal {
   private final UserRepository userRepository;

    protected User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username" + username));
    }
}
