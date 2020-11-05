package com.poker.game.service;

import com.poker.game.entities.User;
import com.poker.game.exceptions.InvalidDataException;
import com.poker.game.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void testCreateUserAccount() {
        User user = User.builder().name("some user")
                .email("email@mail.com")
                .username("username")
                .password("password")
                .build();
        userService.createPlayerAccountAndGetToken(user);
        assertNotNull(user.getId());
        assertTrue(userRepository.findByEmailOrUsername(user.getUsername()).isPresent());
        assertTrue(userRepository.findByEmailOrUsername(user.getEmail()).isPresent());
    }

    @Test
    @Transactional
    void testCreatePlayerAccount_ShouldNotAcceptDuplicateEmailOrUsername() {
        User firstUser = User.builder().name("some user")
                .email("email@mail.com")
                .username("username")
                .password("password")
                .build();
        userService.createPlayerAccountAndGetToken(firstUser);
        User secondUser = User.builder().name("second user")
                .username("secondUsername")
                .email("email@mail.com")
                .password("password").build();
        assertThrows(InvalidDataException.class, () -> userService.createPlayerAccountAndGetToken(secondUser));
        User thirdUser = User.builder().name("second user")
                .username("secondUsername")
                .email("email@mail.com")
                .password("password").build();
        assertThrows(InvalidDataException.class, () -> userService.createPlayerAccountAndGetToken(thirdUser));

    }
}
