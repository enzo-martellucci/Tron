package com.uphf.tron.controller;

import com.uphf.tron.entity.Moto;
import com.uphf.tron.entity.Skin;
import com.uphf.tron.service.ShopService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
    private Label lblMoto;
    @FXML
    private ProgressBar pbSpeedMoto;

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
        list.clear();
        for (Moto moto : mapMotoOwned.keySet())
        {
            StackPane stackPane = new StackPane();
            stackPane.getStyleClass().add("moto-item");
            stackPane.setOnMouseClicked(e -> this.selectMoto(moto));

            Image image = new Image(new ByteArrayInputStream(moto.getDefaultSkin().getRawImage()), LIST_MOTO_SIZE, LIST_MOTO_SIZE, true, true);
            ImageView imageView = new ImageView(image);
            stackPane.getChildren().add(imageView);

            if (!mapMotoOwned.get(moto))
            {
                stackPane.setOpacity(0.4);
                FontIcon icon = new FontIcon(FontAwesomeSolid.LOCK);
                icon.setIconSize(40);
                stackPane.getChildren().add(icon);
            }

            list.add(stackPane);
        }

        this.selectMoto(mapMotoOwned.firstKey());
    }

    private void selectMoto(Moto moto)
    {
        this.moto = moto;
        this.motoOwned = this.shopService.ownMoto(moto);
        this.lblMoto.setText(moto.getName());
        this.pbSpeedMoto.setProgress(moto.getSpeed() / 2);
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
        this.paneSkin.setOpacity(skinOwned ? 1 : 0.4);
        this.lblPrice.setOpacity(skinOwned ? 0.4 : 1);
        this.btnUnlock.setOpacity(skinOwned ? 0.4 : 1);
        this.btnUnlock.setDisable(skinOwned);
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
                Moto moto = this.moto;
                this.initialize(null, null);
                this.selectMoto(moto);
            }

            this.lblError.setText("");
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
