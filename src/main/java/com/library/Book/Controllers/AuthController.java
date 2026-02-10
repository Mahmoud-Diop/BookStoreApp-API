package com.library.Book.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.library.Book.Service.AuthResponse;
import com.library.Book.Service.AuthService;
import com.library.Book.model.Users;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Endpoint pour l'inscription
    @PostMapping("/register")
    public AuthResponse register(@RequestBody Users user) {
        return authService.register(user); // passe par le service qui encode et génère le token
    }

    // Endpoint pour le login
    @PostMapping("/login")
    public AuthResponse login(@RequestBody Users user) {
        return authService.verify(user); // passe par le service qui authentifie et génère le token
    }
}
