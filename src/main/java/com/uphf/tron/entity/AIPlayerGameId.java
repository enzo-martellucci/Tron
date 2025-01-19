package com.uphf.tron.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class AIPlayerGameId
{
    private Long game;
    private Long aiPlayer;
}
