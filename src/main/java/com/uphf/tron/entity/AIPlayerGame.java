package com.uphf.tron.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ai_player_game")
@IdClass(AIPlayerGameId.class)
@Data
public class AIPlayerGame
{
    @Id
    @ManyToOne
    @JoinColumn(name = "id_game")
    private Game game;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_ai_player")
    private AIPlayer aiPlayer;

}
