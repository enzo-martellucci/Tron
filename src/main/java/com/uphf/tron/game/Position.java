package com.uphf.tron.game;

import com.uphf.tron.game.constants.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Position
{
    private int x;
    private int y;

    public Position getNext(Direction direction)
    {
        Position next = null;

        switch (direction)
        {
            case UP -> next = new Position(this.x - 1, this.y);
            case DOWN -> next = new Position(this.x + 1, this.y);
            case LEFT -> next = new Position(this.x, this.y - 1);
            case RIGHT -> next = new Position(this.x, this.y + 1);
        }

        return next;
    }
}
