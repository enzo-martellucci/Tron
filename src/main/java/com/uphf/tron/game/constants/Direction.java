package com.uphf.tron.game.constants;

public enum Direction
{
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public boolean isOpposite(Direction d2)
    {
        return Direction.isOpposite(this, d2);
    }

    public static boolean isOpposite(Direction d1, Direction d2)
    {
        return Direction.getOpposite(d1) == d2;
    }

    public static Direction getOpposite(Direction d)
    {
        if (d == UP)
            return DOWN;
        if (d == DOWN)
            return UP;
        if (d == RIGHT)
            return LEFT;
        if (d == LEFT)
            return RIGHT;
        return UP;
    }
}
