package com.uphf.tron.game.constants;

import static com.uphf.tron.game.constants.CellType.HORIZONTAL;
import static com.uphf.tron.game.constants.CellType.VERTICAL;

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

    public static CellType getCellType(Direction from, Direction to)
    {
        if (from == UP && to == LEFT || from == LEFT && to == UP)
            return CellType.LEFT_TO_UP;
        if (from == UP && to == RIGHT || from == RIGHT && to == UP)
            return CellType.UP_TO_RIGHT;
        if (from == RIGHT && to == DOWN || from == DOWN && to == RIGHT)
            return CellType.RIGHT_TO_DOWN;
        if (from == DOWN && to == LEFT || from == LEFT && to == DOWN)
            return CellType.DOWN_TO_LEFT;
        else
            return from == UP || from == DOWN ? VERTICAL : HORIZONTAL;
    }
}
