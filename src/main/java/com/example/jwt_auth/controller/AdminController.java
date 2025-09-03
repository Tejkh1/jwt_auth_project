package com.example.jwt_auth.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @GetMapping("/dashboard")
    public ResponseEntity<String> getDashboard(){
        return ResponseEntity.ok("THis is ADMIN dashboard only accesibleto admin");
    }
}
