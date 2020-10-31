package com.poker.game.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date playDate;

    private boolean ended;

    @ManyToMany()
    @JoinTable(name = "game_players", joinColumns = {
            @JoinColumn(name = "")
    })
    private Set<User> players = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "winner_id", insertable = false)
    private User winner;
}
