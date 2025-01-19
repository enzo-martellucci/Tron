package com.uphf.tron.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "human_player")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class HumanPlayer extends Player
{
    @Column(nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "id_inventory", nullable = false)
    private Inventory inventory;

    @OneToMany(mappedBy = "player")
    private List<Game> lstGame;
}
