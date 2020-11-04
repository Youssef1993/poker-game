package com.poker.game.web.dto;

import com.poker.game.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserSubscription {

    @NotBlank
    private String name;
    @NotBlank
    @Length(min=2, max=255)
    private String username;
    @NotBlank
    @Email
    @Length(max=255)
    private String email;
    @NotBlank
    @Length(min=5, max=255)
    private String password;

    public User toEntity() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(username);
        return user;
    }
}
