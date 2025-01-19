package com.uphf.tron.entity;

import com.uphf.tron.constants.Difficulty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "ai_player")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class AIPlayer extends Player
{
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @OneToMany(mappedBy = "aiPlayer")
    private List<AIPlayerGame> lstAiPlayerGame;
}
