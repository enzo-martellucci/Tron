package com.uphf.tron.game.constants;

import static com.uphf.tron.game.constants.Direction.*;

public enum CellType
{
    EMPTY,
    VERTICAL,
    HORIZONTAL,
    LEFT_TO_UP,
    UP_TO_RIGHT,
    RIGHT_TO_DOWN,
    DOWN_TO_LEFT;

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
