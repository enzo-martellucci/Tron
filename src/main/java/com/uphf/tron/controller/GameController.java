package com.uphf.tron.controller;

import com.uphf.tron.entity.Moto;
import com.uphf.tron.entity.Skin;
import com.uphf.tron.game.Cell;
import com.uphf.tron.game.Position;
import com.uphf.tron.game.Rider;
import com.uphf.tron.game.Tron;
import com.uphf.tron.game.constants.Difficulty;
import com.uphf.tron.game.strategy.HumanStrategy;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class GameController implements Initializable
{
    private static final Color BACKGROUND_COLOR = Color.rgb(1, 22, 30);
    private static final Color TILE_COLOR = Color.rgb(89, 131, 146);
    private static final double GUTTER = 5;
    private static final double TRAIL = 7;
    private static final double HALF_TRAIL = TRAIL / 2;

    private final SceneController sceneController;

    private AnimationTimer animation;

    private Tron tron;
    private HumanStrategy strategy;

    @FXML
    private Canvas canvas;

    public GameController(SceneController sceneController)
    {
        this.sceneController = sceneController;

        this.animation = new AnimationTimer()
        {
            @Override
            public void handle(long l)
            {
                System.out.println(l);
            }
        };
    }

    public void init(Moto moto, Skin skin, List<Moto> lstAIMoto, List<Skin> lstAISkin, List<Difficulty> lstAIDifficulty)
    {
        this.tron = new Tron(moto, skin, lstAIMoto, lstAISkin, lstAIDifficulty);
        this.strategy = this.tron.getHumanStrategy();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.repaint();
    }

    public void repaint()
    {
        GraphicsContext g = this.canvas.getGraphicsContext2D();

        Cell[][] grid = this.tron.getGrid();
        Rider[] lstRider = this.tron.getLstRider();

        int row = grid.length, col = grid[0].length;
        double w = canvas.getWidth(), h = canvas.getHeight();
        double cell = Math.min((w - (row - 1) * GUTTER) / row, (h - (col - 1) * GUTTER) / col);
        double half = cell / 2;
        double full = cell + GUTTER;

        // Painting Background
        g.setFill(BACKGROUND_COLOR);
        g.fillRect(0, 0, w, h);

        // Painting Tiles
        g.setFill(TILE_COLOR);

        g.save();
        g.translate((-w / row) / 2, (-h / col) / 2);
        for (int r = 0; r <= row; r++)
            for (int c = 0; c <= col; c++)
                g.strokeRoundRect(r * full, c * full, cell, cell, 5, 5);
        g.restore();

        // Painting Walls
        for (int r = 0; r < grid.length; r++)
            for (int c = 0; c < grid[0].length; c++)
            {
                g.setFill(grid[r][c].getColor());
                switch (grid[r][c].getCellType())
                {
                    case HORIZONTAL -> g.fillRect(r * full, c * full + half - HALF_TRAIL, cell, TRAIL);
                    case VERTICAL -> g.fillRect(r * full + half - HALF_TRAIL, c * full, TRAIL, cell);
                    case LEFT_TO_UP ->
                    {
                        g.fillRect(r * full, c * full + half - HALF_TRAIL, half + HALF_TRAIL, TRAIL);
                        g.fillRect(r * full + half - HALF_TRAIL, c * full, TRAIL, half + HALF_TRAIL);
                    }
                    case UP_TO_RIGHT ->
                    {
                        g.fillRect(r * full + half - HALF_TRAIL, c * full + half - HALF_TRAIL, half + HALF_TRAIL, TRAIL);
                        g.fillRect(r * full + half - HALF_TRAIL, c * full, TRAIL, cell / 2 + HALF_TRAIL);
                    }
                    case RIGHT_TO_DOWN ->
                    {
                        g.fillRect(r * full + half - HALF_TRAIL, c * full + half - HALF_TRAIL, half + HALF_TRAIL, TRAIL);
                        g.fillRect(r * full + half - HALF_TRAIL, c * full + half - HALF_TRAIL, TRAIL, cell / 2 + HALF_TRAIL);
                    }
                    case DOWN_TO_LEFT ->
                    {
                        g.fillRect(r * full, c * full + half - HALF_TRAIL, half + HALF_TRAIL, TRAIL);
                        g.fillRect(r * full + half - HALF_TRAIL, c * full + half - HALF_TRAIL, TRAIL, cell / 2 + HALF_TRAIL);
                    }
                }
            }

        // Painting Motos
        for (Rider rider : lstRider)
        {
            Position p = rider.getPosition();
            double x = p.getX() * full;
            double y = p.getY() * full;

            g.save();
            g.translate(x + cell / 2, y + cell / 2);
            switch (rider.getNextDirection())
            {
                case LEFT -> g.rotate(270);
                case DOWN -> g.rotate(180);
                case RIGHT -> g.rotate(90);
            }
            g.drawImage(rider.getImage(), -cell / 2, -cell / 2, cell, cell);
            g.restore();
        }
    }

    public void goToHome()
    {
        this.sceneController.setScene("home");
    }
}
