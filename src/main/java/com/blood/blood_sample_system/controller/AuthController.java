package com.blood.blood_sample_system.controller;

import com.blood.blood_sample_system.dto.AuthDTO.*;
import com.blood.blood_sample_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;
    //POST /api/auth/register
    @PostMapping("/register")
    //http response containing authres obj
    public ResponseEntity<AuthResponse> response(
            @Valid
            @RequestBody
            RegisterRequest request){
        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    //POST /api/auth/login
    @PostMapping("/login")
    //http res containing AuthResponse obj
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody
            LoginRequest request){
        return ResponseEntity.ok(
                authService.login(request));
    }

}
