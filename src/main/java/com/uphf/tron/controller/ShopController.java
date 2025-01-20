package com.uphf.tron.controller;

import com.uphf.tron.entity.Moto;
import com.uphf.tron.entity.Skin;
import com.uphf.tron.service.ShopService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.stereotype.Controller;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.SortedMap;

@Controller
public class ShopController implements Initializable
{
    private static final double LIST_MOTO_SIZE = 128.0;

    private final SceneController sceneController;
    private final ShopService shopService;

    private Moto moto;
    private boolean motoOwned;
    private Skin skin;

    @FXML
    private VBox vBoxMoto;

    @FXML
    private StackPane paneSkin;
    @FXML
    private ImageView viewSkin;
    @FXML
    private Rectangle rectTrail;
    @FXML
    private FontIcon iconLock;
    @FXML
    private Label lblSkin;
    @FXML
    private Label lblPrice;
    @FXML
    private Button btnUnlock;

    @FXML
    private Label lblError;
    @FXML
    private Label lblCoins;

    public ShopController(SceneController sceneController, ShopService shopService)
    {
        this.sceneController = sceneController;
        this.shopService = shopService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.initializeMotoList();
        this.updateCoins();
    }

    private void initializeMotoList()
    {
        SortedMap<Moto, Boolean> mapMotoOwned = this.shopService.getAllMotoOwned();

        ObservableList<Node> list = this.vBoxMoto.getChildren();
        for (Moto moto : mapMotoOwned.keySet())
        {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.getStyleClass().add("moto-item");

            Image image = new Image(new ByteArrayInputStream(moto.getDefaultSkin().getRawImage()), LIST_MOTO_SIZE, LIST_MOTO_SIZE, true, true);
            ImageView imageView = new ImageView(image);
            stackPane.getChildren().add(imageView);

            if (!mapMotoOwned.get(moto))
            {
                stackPane.getStyleClass().add("disable");
                FontIcon icon = new FontIcon(FontAwesomeSolid.LOCK);
                icon.setIconSize(40);
                stackPane.getChildren().add(icon);
            }

            Button button = new Button();
            button.setOnAction(e -> this.selectMoto(moto));
            button.setOpacity(0);
            button.setMaxHeight(Double.MAX_VALUE);
            button.setMaxWidth(Double.MAX_VALUE);
            stackPane.getChildren().add(button);

            list.add(stackPane);
        }

        this.selectMoto(mapMotoOwned.firstKey());
    }

    private void selectMoto(Moto moto)
    {
        this.moto = moto;
        this.motoOwned = this.shopService.ownMoto(moto);
        this.selectSkin(this.moto.getDefaultSkin());
        this.lblError.setText("");
    }

    private void selectSkin(Skin skin)
    {
        this.skin = skin;
        boolean skinOwned = this.shopService.ownSkin(skin);

        this.lblError.setText("");

        this.viewSkin.setImage(new Image(new ByteArrayInputStream(skin.getRawImage())));
        this.rectTrail.setFill(Color.valueOf(skin.getColor()));
        this.lblSkin.setText(skin.getName());
        this.lblPrice.setText("" + (this.motoOwned ? skin.getPrice() : this.moto.getPrice()));

        this.iconLock.setVisible(!skinOwned);
        if (!skinOwned)
            this.paneSkin.getStyleClass().add("disable");
        else
            this.paneSkin.getStyleClass().clear();
        this.lblPrice.setVisible(!skinOwned);
        this.btnUnlock.setVisible(!skinOwned);
    }

    public void prevSkin()
    {
        this.selectSkin(this.shopService.prevSkin(this.moto, this.skin));
    }

    public void nextSkin()
    {
        this.selectSkin(this.shopService.nextSkin(this.moto, this.skin));
    }

    public void unlock()
    {
        try
        {
            if (this.motoOwned) {
                this.shopService.unlockSkin(this.skin);
                this.selectSkin(this.skin);
            } else {
                this.shopService.unlockMoto(this.moto);
                this.selectMoto(this.moto);
            }

            this.updateCoins();
        } catch (IllegalArgumentException e){ this.lblError.setText(e.getMessage()); }
    }

    public void updateCoins()
    {
        this.lblCoins.setText("" + this.shopService.getCoins());
    }

    public void goToHome()
    {
        this.sceneController.setScene("home");
    }
}
