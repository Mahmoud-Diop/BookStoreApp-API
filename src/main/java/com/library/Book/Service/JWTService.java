package com.library.Book.Service;

import java.security.Key;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secretKey;

    public JWTService() {

          
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // Ajouter des claims personnalisés
        claims.put("roles", userDetails.getAuthorities()); // les rôles/permissions
        claims.put("accountNonExpired", userDetails.isAccountNonExpired());
        claims.put("accountNonLocked", userDetails.isAccountNonLocked());
        claims.put("credentialsNonExpired", userDetails.isCredentialsNonExpired());
        claims.put("enabled", userDetails.isEnabled());

        return Jwts.builder()
                .setClaims(claims) // claims personnalisés
                .setSubject(userDetails.getUsername()) // sujet = username/email
                .setIssuedAt(new Date(System.currentTimeMillis())) // date de création
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // expiration = 10h
                .signWith(getKey(), SignatureAlgorithm.HS256) // signature avec clé secrète
                .compact();
    }

    public Key getKey() {
        byte[] KeyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(KeyBytes);

    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // le "subject" correspond au username
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date(System.currentTimeMillis()));
    }

}
