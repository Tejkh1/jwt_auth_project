

package com.example.jwt_auth.dto;
import com.example.jwt_auth.config.JwtUtil;

public class LoginRequest {
    private String email;
    private String password;

    // Default constructor
    public LoginRequest() {}

    // Parameterized constructor (optional)
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

  //  String accessToken = JwtUtil.generateToken(user.getEmail());
   // String refreshToken = jwtUtil.generateRefreshToken(user.getEmail()); // new method

    // Getters and Setters

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}