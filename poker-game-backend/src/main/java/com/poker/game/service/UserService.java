package com.poker.game.service;

import com.poker.game.config.security.JwtTokenProvider;
import com.poker.game.entities.User;
import com.poker.game.entities.enums.Role;
import com.poker.game.exceptions.InvalidDataException;
import com.poker.game.exceptions.NotFoundException;
import com.poker.game.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder encoder;

    public String authenticate(String email, String password) {
        User user = repository.findByEmailOrUsername(email)
                .orElseThrow(() -> new NotFoundException(String.format("No user found under id : %s", email)));
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(user.getEmail(), user.getRole());
        }catch (Exception e) {
            throw new NotFoundException("Invalid email/password supplied !");
        }
    }

    @Transactional
    public String createPlayerAccount(User user) {
        if (!isEmailAvailable(user.getEmail())) {
            throw new InvalidDataException("Email already used !!");
        }
        if (!isUsernameAvailable(user.getUsername())) {
            throw new InvalidDataException("Username already used !!");
        }
        String originalPassword = user.getPassword();
        user.setPassword(encoder.encode(originalPassword));
        user.setBalance(1000D);
        user.setRole(Role.ROLE_PLAYER);
        repository.save(user);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), originalPassword));
        return jwtTokenProvider.createToken(user.getEmail(), user.getRole());
    }

    public boolean isEmailAvailable(String email) {
        return !repository.existsByEmail(email);
    }

    public boolean isUsernameAvailable(String username) {
        return !repository.existsByUsername(username);
    }
}
