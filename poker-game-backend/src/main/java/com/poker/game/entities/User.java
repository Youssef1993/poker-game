package com.poker.game.entities;

import com.poker.game.entities.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Double balance;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "username", unique = true)
    private String username;
    private String password;

    @Builder
    public User(Long id, String name, Double balance, Role role, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.role = role;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
