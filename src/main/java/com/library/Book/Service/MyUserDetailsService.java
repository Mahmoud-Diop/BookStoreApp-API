package com.library.Book.Service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.library.Book.Persistence.UserRepository;
import com.library.Book.model.MyUserDetails;
import com.library.Book.model.Users;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
         Users user = userRepository.findByEmail(email).
         orElseThrow(()->new UsernameNotFoundException("User not found with username: " + email));
        
 
        return new MyUserDetails(user);

    }

}
