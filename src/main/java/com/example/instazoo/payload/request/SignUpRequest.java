package com.example.instazoo.payload.request;

import com.example.instazoo.annotations.PasswordMatchers;
import com.example.instazoo.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * This class we use when we were authorize new user
 */
@Data
@PasswordMatchers
public class SignUpRequest {
    @Email(message = "It should had email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;
    @NotEmpty(message = "Please enter your name")
    private String firstname;
    @NotEmpty(message = "Please enter your lastname")
    private String lastname;
    @NotEmpty(message = "Please enter your username")
    private String username;
    @NotEmpty(message = "Please enter your password")
    @Size(min = 6)
    private String password;
    private String confirmPassword;
}
