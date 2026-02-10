package com.library.Book.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.library.Book.Service.JWTService;
import com.library.Book.Service.MyUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private ApplicationContext Context;

    /*
     * La logique:
     * 1)on intercepte les requete des utilisateurs
     * 2) on extrait les infos du token des utilisateur.
     * 3) on verifie si le context n' a pas encore d'utilisteur authentifier
     * 4)si oui on charge cet utilisateur depuis la base
     * 5) on creer un objet d authentification
     * 6) on le stoke dans le contexte
     * 8) on passe au filtre suivant
     **/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 1. Récupérer le header Authorization
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer")) {

            token = authHeader.substring(7);
            username = jwtService.extractUsernameFromToken(token);
        }
        // on Vérifie si le contexte de sécurité Spring n’a pas déjà une
        // authentification en place.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = Context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                /*
                 * construit un objet contenant :
                 * l’adresse IP du client,
                 * l’ID de session,
                 * d’autres métadonnées liées à la requête.
                 * Cela enrichit ton authToken avec des détails utiles pour la sécurité (ex.
                 * traçabilité, logs, restrictions par IP).
                 **/
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }
        filterChain.doFilter(request, response);

    }
}
