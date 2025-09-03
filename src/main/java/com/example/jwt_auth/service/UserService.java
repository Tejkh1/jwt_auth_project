package com.example.jwt_auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.jwt_auth.entity.User;
import com.example.jwt_auth.entity.Role;
import com.example.jwt_auth.repository.UserRepository;


@Service
public class UserService {

    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(User user) {
      user.setPassword(passwordEncoder.encode(user.getPassword())); // encrypt password
        user.setRole(Role.USER); // default role
        userRepository.save(user);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}




