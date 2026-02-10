package com.library.Book.Service;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.Book.DTO.AuthDTO;
import com.library.Book.Persistence.UserRepository;
import com.library.Book.model.Users;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, JWTService jwtService, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    // Inscription
    public AuthDTO register(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Users savedUser = userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getEmail());

        String token = jwtService.generateToken(userDetails);

        return new AuthDTO(token,
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName(),

                savedUser.getRole());
    }

    // Authentification (login)
    public AuthDTO verify(Users user) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()));

        if (authenticate.isAuthenticated()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtService.generateToken(userDetails);

            Users dbUser = userRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            if (dbUser != null) {
                return new AuthDTO(
                        token,
                        dbUser.getUsername(),
                        dbUser.getEmail(),
                        dbUser.getFirstName(),
                        dbUser.getLastName(),
                        dbUser.getRole());

            } else {

                throw new RuntimeException("Utilisateur non trouve");

            }

        } else {
            throw new RuntimeException("Authentication failed");
        }
    }
}