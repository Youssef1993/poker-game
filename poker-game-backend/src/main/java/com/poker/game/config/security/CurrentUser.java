package com.poker.game.config.security;


import com.poker.game.entities.User;
import com.poker.game.exceptions.NotFoundException;
import com.poker.game.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class CurrentUser {

    private User user;
    private final UserRepository userRepository;

    public User get() {
        System.out.println("entered get current user");
        if (user == null) {
            org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            this.user = userRepository.findByEmail(principal.getUsername())
                    .orElseThrow(() -> new NotFoundException("No User found under username " + principal.getUsername()));
        }
        return user;
    }


}
