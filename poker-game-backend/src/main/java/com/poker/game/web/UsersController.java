package com.poker.game.web;

import com.poker.game.service.UserService;
import com.poker.game.web.dto.BoxedValue;
import com.poker.game.web.dto.UserSubscription;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestParam("identifier") @NotBlank String identifier, @RequestParam("password") @NotBlank String password) {
        return new ResponseEntity<>(new BoxedValue<>(userService.authenticate(identifier, password)), HttpStatus.OK);
    }

    @GetMapping("/emails/{email}")
    public ResponseEntity<BoxedValue<Boolean>> isEmailAvailable(@PathVariable("email") @NotBlank String email) {
        return new ResponseEntity<>(new BoxedValue<>(userService.isEmailAvailable(email)), HttpStatus.OK);
    }

    @GetMapping("/usernames/{username}")
    public ResponseEntity<?> isUsernameAvailable(@PathVariable("username") @NotBlank String username) {
        return new ResponseEntity<>(new BoxedValue<>(userService.isUsernameAvailable(username)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BoxedValue<String>> createAccount(@RequestBody @Valid UserSubscription userSubscription) {
        String token = userService.createPlayerAccountAndGetToken(userSubscription.toEntity());
        return new ResponseEntity<>(new BoxedValue<>(token) ,HttpStatus.CREATED);
    }
}
