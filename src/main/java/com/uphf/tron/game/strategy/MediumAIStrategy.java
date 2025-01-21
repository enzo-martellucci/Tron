package com.uphf.tron.game.strategy;

import com.uphf.tron.game.constants.Direction;

public class MediumAIStrategy extends Strategy
{
    @Override
    public Direction getDirection()
    {
        return Direction.UP;
    }
}
