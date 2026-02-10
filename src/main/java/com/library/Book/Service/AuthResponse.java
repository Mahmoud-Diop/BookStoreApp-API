package com.library.Book.Service;
public class AuthResponse {
    private String token;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    public AuthResponse(String token, String username, String email, String firstName, String lastName, String role) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

}
