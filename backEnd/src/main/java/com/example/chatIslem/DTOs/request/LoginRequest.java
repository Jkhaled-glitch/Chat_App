package com.example.chatIslem.DTOs.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
@Data
public class LoginRequest {
    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "password is required")
    private String password;



}
