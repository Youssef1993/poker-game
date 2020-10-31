package com.poker.game.service;

import com.poker.game.config.security.JwtTokenProvider;
import com.poker.game.entities.User;
import com.poker.game.exceptions.NotFoundException;
import com.poker.game.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public String authenticate(String email, String password) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(String.format("No user found under id : %s", email)));
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(user.getEmail(), user.getRole());
        }catch (Exception e) {
            throw new NotFoundException("Invalid email/password supplied !");
        }
    }
}
