package com.uphf.tron.controller;

import com.uphf.tron.entity.AIPlayer;
import com.uphf.tron.entity.Moto;
import com.uphf.tron.entity.Skin;
import com.uphf.tron.game.constants.Difficulty;
import com.uphf.tron.service.SelectionService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.ResourceBundle;

@Controller
public class SelectionController implements Initializable
{
    private final SceneController sceneController;
    private final SelectionService selectionService;
    private final GameController gameController;

    private NavigableMap<Moto, List<Skin>> mapPlayerMotoSkin;
    private Moto moto;
    private Skin skin;
    private Moto motoAI1;
    private Skin skinAI1;
    private Moto motoAI2;
    private Skin skinAI2;
    private Moto motoAI3;
    private Skin skinAI3;

    @FXML
    private ImageView viewMoto;
    @FXML
    private Rectangle rectTrail;
    @FXML
    private Label lblName;
    @FXML
    private ProgressBar pbSpeed;
    @FXML
    private Label lblMoto;
    @FXML
    private Label lblSkin;

    @FXML
    private HBox fullAI1;
    @FXML
    private VBox emptyAI1;
    @FXML
    private ImageView viewAI1Moto;
    @FXML
    private Rectangle rectAI1Trail;
    @FXML
    private Label lblAI1Name;
    @FXML
    private Label lblAI1Difficulty;

    @FXML
    private HBox fullAI2;
    @FXML
    private VBox emptyAI2;
    @FXML
    private ImageView viewAI2Moto;
    @FXML
    private Rectangle rectAI2Trail;
    @FXML
    private Label lblAI2Name;
    @FXML
    private Label lblAI2Difficulty;

    @FXML
    private HBox fullAI3;
    @FXML
    private VBox emptyAI3;
    @FXML
    private ImageView viewAI3Moto;
    @FXML
    private Rectangle rectAI3Trail;
    @FXML
    private Label lblAI3Name;
    @FXML
    private Label lblAI3Difficulty;


    public SelectionController(SceneController sceneController, SelectionService selectionService, GameController gameController)
    {
        this.sceneController = sceneController;
        this.selectionService = selectionService;
        this.gameController = gameController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        // Init player
        this.lblName.setText(this.selectionService.getPlayerName());
        this.mapPlayerMotoSkin = this.selectionService.getAllPlayerAsset();
        this.selectMoto(this.mapPlayerMotoSkin.firstKey());

        // Init ai
        this.setAI(1, Difficulty.EASY);
        this.setAI(2, Difficulty.EASY);
        this.setAI(3, Difficulty.EASY);
        this.removeAI2();
        this.removeAI3();
    }

    private void selectMoto(Moto moto)
    {
        this.moto = moto;

        this.pbSpeed.setProgress(moto.getSpeed() / 2);
        this.lblMoto.setText(moto.getName());
        this.lblSkin.setText(moto.getDefaultSkin().getName());

        this.selectSkin(moto.getDefaultSkin());
    }

    private void selectSkin(Skin skin)
    {
        this.skin = skin;

        this.viewMoto.setImage(new Image(new ByteArrayInputStream(skin.getRawImage())));
        this.rectTrail.setFill(Color.valueOf(skin.getColor()));
        this.lblSkin.setText(skin.getName());
    }

    public void prevMoto()
    {
        Moto moto = this.mapPlayerMotoSkin.navigableKeySet().lower(this.moto);
        this.selectMoto(moto != null ? moto : this.mapPlayerMotoSkin.lastKey());
    }

    public void nextMoto() {
        Moto moto = this.mapPlayerMotoSkin.navigableKeySet().higher(this.moto);
        this.selectMoto(moto != null ? moto : this.mapPlayerMotoSkin.firstKey());
    }

    public void prevSkin()
    {
        List<Skin> lstSkin = this.mapPlayerMotoSkin.get(this.moto);
        for (int i = 0; i < lstSkin.size(); i++)
            if (lstSkin.get(i).getName().equals(this.skin.getName()))
            {
                this.selectSkin(lstSkin.get(i == 0 ? lstSkin.size() - 1: i - 1));
                return;
            }
    }

    public void nextSkin()
    {
        List<Skin> lstSkin = this.mapPlayerMotoSkin.get(this.moto);
        for (int i = 0; i < lstSkin.size(); i++)
            if (lstSkin.get(i).getName().equals(this.skin.getName()))
            {
                this.selectSkin(lstSkin.get((i + 1) % lstSkin.size()));
                return;
            }
    }

    public void addAI1() { this.addAI(this.emptyAI1, this.fullAI1); }
    public void removeAI1() { this.removeAI(this.emptyAI1, this.fullAI1); }
    public void prevAI1Difficulty() { this.prevAIDifficulty(1, this.lblAI1Difficulty.getText()); }
    public void nextAI1Difficulty() { this.nextAIDifficulty(1, this.lblAI1Difficulty.getText()); }

    public void addAI2() { this.addAI(this.emptyAI2, this.fullAI2); }
    public void removeAI2() { this.removeAI(this.emptyAI2, this.fullAI2); }
    public void prevAI2Difficulty() { this.prevAIDifficulty(2, this.lblAI2Difficulty.getText()); }
    public void nextAI2Difficulty() { this.nextAIDifficulty(2, this.lblAI2Difficulty.getText()); }

    public void addAI3() { this.addAI(this.emptyAI3, this.fullAI3); }
    public void removeAI3() { this.removeAI(this.emptyAI3, this.fullAI3); }
    public void prevAI3Difficulty() { this.prevAIDifficulty(3, this.lblAI3Difficulty.getText()); }
    public void nextAI3Difficulty() { this.nextAIDifficulty(3, this.lblAI3Difficulty.getText()); }

    private void addAI(VBox empty, HBox full)
    {
        empty.setVisible(false);
        full.setVisible(true);
    }

    private void removeAI(VBox empty, HBox full)
    {
        empty.setVisible(true);
        full.setVisible(false);
    }

    private void setAI(int ai, Difficulty difficulty)
    {
        AIPlayer aiPlayer = this.selectionService.getRandomAI(difficulty);
        Moto moto = this.selectionService.getRandomMoto();
        Skin skin = this.selectionService.getRandomSkin(moto);

        switch (ai)
        {
            case 1 ->
            {
                this.motoAI1 = moto;
                this.skinAI1 = skin;
                this.lblAI1Name.setText(aiPlayer.getUsername());
                this.lblAI1Difficulty.setText(aiPlayer.getDifficulty().toString());
                this.viewAI1Moto.setImage(new Image(new ByteArrayInputStream(skin.getRawImage())));
                this.rectAI1Trail.setFill(Color.valueOf(skin.getColor()));
            }
            case 2 ->
            {
                this.motoAI2 = moto;
                this.skinAI2 = skin;
                this.lblAI2Name.setText(aiPlayer.getUsername());
                this.lblAI2Difficulty.setText(aiPlayer.getDifficulty().toString());
                this.viewAI2Moto.setImage(new Image(new ByteArrayInputStream(skin.getRawImage())));
                this.rectAI2Trail.setFill(Color.valueOf(skin.getColor()));
            }
            case 3 ->
            {
                this.motoAI3 = moto;
                this.skinAI3 = skin;
                this.lblAI3Name.setText(aiPlayer.getUsername());
                this.lblAI3Difficulty.setText(aiPlayer.getDifficulty().toString());
                this.viewAI3Moto.setImage(new Image(new ByteArrayInputStream(skin.getRawImage())));
                this.rectAI3Trail.setFill(Color.valueOf(skin.getColor()));
            }
        }
    }

    private void prevAIDifficulty(int ai, String difficulty)
    {
        Difficulty d = Difficulty.valueOf(difficulty);
        switch (d)
        {
            case EASY -> this.setAI(ai, Difficulty.HARD);
            case MEDIUM -> this.setAI(ai, Difficulty.EASY);
            case HARD -> this.setAI(ai, Difficulty.MEDIUM);
        }
    }

    private void nextAIDifficulty(int ai, String difficulty)
    {
        Difficulty d = Difficulty.valueOf(difficulty);
        switch (d)
        {
            case EASY -> this.setAI(ai, Difficulty.MEDIUM);
            case MEDIUM -> this.setAI(ai, Difficulty.HARD);
            case HARD -> this.setAI(ai, Difficulty.EASY);
        }
    }

    public void goToHome()
    {
        this.sceneController.setScene("home");
    }

    public void startGame()
    {
        List<Moto> lstAIMoto = new ArrayList<>();
        List<Skin> lstAISkin = new ArrayList<>();
        List<Difficulty> lstAIDifficulty = new ArrayList<>();

        lstAIMoto.add(this.motoAI1);
        lstAISkin.add(this.skinAI1);
        lstAIDifficulty.add(Difficulty.valueOf(this.lblAI1Difficulty.getText()));

        if (this.fullAI2.isVisible())
        {
            lstAIMoto.add(this.motoAI2);
            lstAISkin.add(this.skinAI2);
            lstAIDifficulty.add(Difficulty.valueOf(this.lblAI2Difficulty.getText()));
        }

        if (this.fullAI3.isVisible())
        {
            lstAIMoto.add(this.motoAI3);
            lstAISkin.add(this.skinAI3);
            lstAIDifficulty.add(Difficulty.valueOf(this.lblAI3Difficulty.getText()));
        }

        this.gameController.init(this.moto, this.skin, lstAIMoto, lstAISkin, lstAIDifficulty);
        this.sceneController.setScene("game");
    }
}
