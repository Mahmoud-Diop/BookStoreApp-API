package com.library.Book.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.library.Book.Service.UserService;
import com.library.Book.model.Users;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return userService.register(user); // passe par le service qui encode

    }
}
