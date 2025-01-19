package com.uphf.tron.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "game")
@Data
public class Game
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "id_player", nullable = false)
    private Player player;

    @Column(nullable = false)
    private Integer rank;

    @OneToMany(mappedBy = "game")
    private List<AIPlayerGame> lstAiPlayerGame;
}
