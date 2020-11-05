package com.poker.game.service;

import com.poker.game.config.security.JwtTokenProvider;
import com.poker.game.entities.User;
import com.poker.game.entities.enums.Role;
import com.poker.game.exceptions.InvalidDataException;
import com.poker.game.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userRepository, authenticationManager, jwtTokenProvider, passwordEncoder);
    }

    @Test
    void testCreatePlayerAccount_ShouldThrowExceptionForExistingEmail() {
        final String email = "example@mail.com";
        User user = User.builder().email(email).build();
        when(userRepository.existsByEmail(email)).thenReturn(true);
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class, () -> userService.createPlayerAccountAndGetToken(user));
        verify(userRepository, Mockito.times(1)).existsByEmail(email);
        assertTrue(invalidDataException.getMessage().contains("Email already used"));
    }

    @Test
    void testCreatePlayerAccount_ShouldThrowExceptionForExistingUsername() {
        final String username = "username";
        User user = User.builder().username(username).build();
        when(userRepository.existsByUsername(username)).thenReturn(true);
        InvalidDataException invalidDataException = assertThrows(InvalidDataException.class, () -> userService.createPlayerAccountAndGetToken(user));
        verify(userRepository, Mockito.times(1)).existsByUsername(username);
        assertTrue(invalidDataException.getMessage().contains("Username already used"));
    }

    @Test
    void testCreatePlayerAccount_ShouldWorkWithValidDate() {
        User user = User.builder().username("username")
                .password("password")
                .email("example@mail.com")
                .name("Some user")
                .build();
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("Encrypted Password");
        when(userRepository.save(user)).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtTokenProvider.createToken(user.getEmail(), Role.ROLE_PLAYER)).thenReturn("token");
        String token = userService.createPlayerAccountAndGetToken(user);
        assertEquals(token, "token", "valid token returned");
        assertEquals(user.getPassword(), "Encrypted Password", "password was encrypted");
        assertEquals(user.getRole(), Role.ROLE_PLAYER, "Role has been affected correctly");
        assertEquals(user.getBalance(), 1000D);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testIsEmailAvailable() {
        when(userRepository.existsByEmail("new@email.com")).thenReturn(false);
        assertTrue(userService.isEmailAvailable("new@email.com"));
        when(userRepository.existsByEmail("existing@email.com")).thenReturn(true);
        assertFalse(userService.isEmailAvailable("existing@email.com"));
    }

    @Test
    void testIsUsernameAvailable() {
        when(userRepository.existsByUsername("new")).thenReturn(false);
        assertTrue(userService.isUsernameAvailable("new"));
        when(userRepository.existsByUsername("existing")).thenReturn(true);
        assertFalse(userService.isUsernameAvailable("existing"));
    }

}