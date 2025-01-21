package com.uphf.tron.game.strategy;

import com.uphf.tron.game.constants.Direction;

public class HumanStrategy extends Strategy
{
    private Direction direction;

    public HumanStrategy()
    {
        this.direction = Direction.RIGHT;
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    @Override
    public Direction getDirection()
    {
        return this.direction;
    }
}
