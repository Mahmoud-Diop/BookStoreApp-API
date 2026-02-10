package com.library.Book.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.library.Book.DTO.AuthDTO;
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

    @PostMapping("/register")
    public AuthDTO register(@RequestBody Users user) {
        return authService.register(user);
    }

    @PostMapping("/login")
    public AuthDTO login(@RequestBody Users user) {
        return authService.verify(user);
    }
}

