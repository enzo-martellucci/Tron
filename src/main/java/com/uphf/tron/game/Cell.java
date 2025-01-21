package com.uphf.tron.game;

import com.uphf.tron.game.constants.CellType;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cell
{
    private CellType cellType;
    private Color color;
}
