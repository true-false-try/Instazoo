package com.example.instazoo.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * This class we give for web service when we were tried to authorize
 */
@Data
public class LoginRequest {
    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    private String password;

}
