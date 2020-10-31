package com.poker.game.repository;

import com.poker.game.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where LOWER(u.email) = lower(:username)")
    Optional<User> findByEmail(@Param("username") String username);
}
