package com.poker.game.repository;

import com.poker.game.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where LOWER(u.email) = lower(:username) or LOWER(u.username) = lower(:username)")
    Optional<User> findByEmailOrUsername(@Param("username") String username);

    @Query("select case when count(u) > 0 then true else false end from User u where LOWER(u.email) = lower(:email)")
    boolean existsByEmail(@Param("email") String email);

    @Query("select case when count(u) > 0 then true else false end from User u where LOWER(u.username) = lower(:username)")
    boolean existsByUsername(@Param("username") String username);
}
