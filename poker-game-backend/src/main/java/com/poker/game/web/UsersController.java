package com.poker.game.web;

import com.poker.game.service.UserService;
import com.poker.game.web.dto.BoxedValue;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestParam("email") String email, @RequestParam("password") String password) {
        return new ResponseEntity<>(new BoxedValue<>(userService.authenticate(email, password)), HttpStatus.OK);
    }
}
