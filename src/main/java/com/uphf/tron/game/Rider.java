package com.uphf.tron.game;

import com.uphf.tron.entity.Moto;
import com.uphf.tron.entity.Skin;
import com.uphf.tron.game.constants.Direction;
import com.uphf.tron.game.strategy.Strategy;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lombok.Data;

import java.io.ByteArrayInputStream;

@Data
public class Rider
{
    private Tron listener;

    private Strategy strategy;
    private Position position;
    private Direction prevDirection;
    private Direction nextDirection;

    private double speed;
    private Image image;
    private Color color;

    private Timeline timeline;

    public Rider(Tron listener, Strategy strategy, Position position, Direction direction, Moto moto, Skin skin)
    {
        this.listener = listener;

        this.strategy = strategy;
        this.position = position;
        this.prevDirection = direction;
        this.nextDirection = direction;

        this.speed = moto.getSpeed();
        this.image = new Image(new ByteArrayInputStream(skin.getRawImage()));
        this.color = Color.valueOf(skin.getColor());

        this.timeline = new Timeline(new KeyFrame(Duration.millis(1000 / speed), this::next));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void start()
    {
        this.timeline.play();
    }

    private void next(ActionEvent actionEvent)
    {

    }

    public void stop()
    {
        this.timeline.stop();
    }
}
