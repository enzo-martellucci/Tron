package com.uphf.tron.game;

import com.uphf.tron.entity.Moto;
import com.uphf.tron.entity.Skin;
import com.uphf.tron.game.constants.CellType;
import com.uphf.tron.game.constants.Difficulty;
import com.uphf.tron.game.constants.Direction;
import com.uphf.tron.game.strategy.*;
import javafx.scene.paint.Color;

import java.util.List;

public class Tron
{
    public static final int NB_ROW = 20;
    public static final int NB_COL = 20;
    private static final Position[] LST_START = new Position[]{ new Position(4, 4), new Position(NB_COL - 3, 5), new Position(2, NB_ROW - 2), new Position(NB_COL - 3, NB_ROW - 5) };
    private static final Direction[] LST_DIRECTION = new Direction[]{ Direction.DOWN, Direction.DOWN, Direction.LEFT, Direction.LEFT };

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

        this.grid[2][2] = new Cell(CellType.HORIZONTAL, Color.valueOf(skin.getColor()));
        this.grid[3][2] = new Cell(CellType.HORIZONTAL, Color.valueOf(skin.getColor()));
        this.grid[4][2] = new Cell(CellType.DOWN_TO_LEFT, Color.valueOf(skin.getColor()));
        this.grid[4][3] = new Cell(CellType.VERTICAL, Color.valueOf(skin.getColor()));

        this.grid[NB_ROW - 3][2] = new Cell(CellType.VERTICAL, this.lstRider[1].getColor());
        this.grid[NB_ROW - 3][3] = new Cell(CellType.VERTICAL, this.lstRider[1].getColor());
        this.grid[NB_ROW - 3][4] = new Cell(CellType.VERTICAL, this.lstRider[1].getColor());

        if (this.lstRider.length > 2)
        {
            this.grid[2][NB_ROW - 3] = new Cell(CellType.HORIZONTAL, this.lstRider[2].getColor());
            this.grid[3][NB_ROW - 3] = new Cell(CellType.HORIZONTAL, this.lstRider[2].getColor());
            this.grid[4][NB_ROW - 3] = new Cell(CellType.DOWN_TO_LEFT, this.lstRider[2].getColor());
            this.grid[4][NB_ROW - 2] = new Cell(CellType.LEFT_TO_UP, this.lstRider[2].getColor());
            this.grid[3][NB_ROW - 2] = new Cell(CellType.HORIZONTAL, this.lstRider[2].getColor());
        }

        if (this.lstRider.length > 3)
        {
            this.grid[NB_COL - 3][NB_ROW - 3] = new Cell(CellType.VERTICAL, this.lstRider[3].getColor());
            this.grid[NB_COL - 3][NB_ROW - 4] = new Cell(CellType.VERTICAL, this.lstRider[3].getColor());
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

        this.grid[x][y].setCellType(CellType.getCellType(rider.getPrevDirection(), rider.getNextDirection()));
        this.grid[x][y].setColor(rider.getColor());
        rider.setPrevDirection(rider.getNextDirection());
        rider.setPosition(next);
    }
}
