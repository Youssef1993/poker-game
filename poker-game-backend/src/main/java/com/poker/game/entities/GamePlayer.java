package com.poker.game.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class GamePlayer {

    @EmbeddedId
    private GamePlayerId id = new GamePlayerId();
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("player_id")
    private User player;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("game_id")
    private Game game;

    
}
