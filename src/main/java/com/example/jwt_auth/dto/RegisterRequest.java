package com.example.jwt_auth.dto;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String role;   //USER  or ADMIN
}