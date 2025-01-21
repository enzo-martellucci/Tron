package com.uphf.tron.game;

import com.uphf.tron.entity.Moto;
import com.uphf.tron.entity.Skin;
import com.uphf.tron.game.constants.CellType;
import com.uphf.tron.game.constants.Difficulty;
import com.uphf.tron.game.constants.Direction;
import com.uphf.tron.game.strategy.*;

import java.util.List;

public class Tron
{
    private static final int NB_ROW = 12;
    private static final int NB_COL = 12;
    private static final Position[] LST_START = new Position[]{ new Position(2, 2), new Position(9, 2), new Position(2, 9), new Position(9, 9) };
    private static final Direction[] LST_DIRECTION = new Direction[]{ Direction.RIGHT, Direction.DOWN, Direction.UP, Direction.LEFT };


    private Cell[][] grid;
    private Rider[] lstRider;

    public Tron(Moto moto, Skin skin, List<Moto> lstAIMoto, List<Skin> lstAISkin, List<Difficulty> lstAIDifficulty)
    {
        this.grid = new Cell[NB_ROW][NB_COL];
        for (int r = 0; r < this.grid.length; r++)
            for (int c = 0; c < this.grid[r].length; c++)
                this.grid[r][c] = new Cell(CellType.EMPTY, null);

        this.lstRider = new Rider[lstAIMoto.size() + 1];
        this.lstRider[0] = new Rider(this, new HumanStrategy(), LST_START[0], LST_DIRECTION[0], moto, skin);
        for (int i = 0; i < lstAIMoto.size(); i++)
            this.lstRider[i + 1] = new Rider(this, this.getStrategy(lstAIDifficulty.get(i)), LST_START[i + 1], LST_DIRECTION[i + 1], lstAIMoto.get(i), lstAISkin.get(i));

        for (Rider rider : this.lstRider)
        {
            Position p = rider.getPosition();
            this.grid[p.getX()][p.getY()].setCellType(Direction.getCellType(rider.getPrevDirection(), rider.getNextDirection()));
            this.grid[p.getX()][p.getY()].setColor(rider.getColor());
        }
    }

    private Strategy getStrategy(Difficulty difficulty)
    {
        if (difficulty == Difficulty.MEDIUM)
            return new MediumAIStrategy();
        if (difficulty == Difficulty.HARD)
            return new HardAIStrategy();
        return new EasyAIStrategy();
    }

    public HumanStrategy getHumanStrategy()
    {
        return (HumanStrategy) this.lstRider[0].getStrategy();
    }

    public Cell[][] getGrid(){ return this.grid; }
    public Rider[] getLstRider(){ return this.lstRider; }

    public void start()
    {
        for (Rider rider : this.lstRider)
            rider.start();
    }

    public void next(Rider rider)
    {
        Position prev = rider.getPosition();
        int x = prev.getX(), y = prev.getY();
        Position next = prev.getNext(rider.getNextDirection());
        int nextX = next.getX(), nextY = next.getY();

        if (nextX < 0 || nextX >= this.grid.length || nextY < 0 || nextY >= this.grid[0].length)
            rider.stop();

        this.grid[x][y].setCellType(Direction.getCellType(rider.getPrevDirection(), rider.getNextDirection()));
        this.grid[x][y].setColor(rider.getColor());
        rider.setPrevDirection(rider.getNextDirection());
        rider.setPosition(next);
    }
}
