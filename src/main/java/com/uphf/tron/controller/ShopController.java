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
import java.util.List;
import javafx.scene.shape.Rectangle;

import java.util.ResourceBundle;
import java.util.SortedMap;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


@Controller
public class ShopController implements Initializable
{
    private static final double LIST_MOTO_SIZE = 128.0;

    private final SceneController sceneController;
    private final ShopService shopService;
    @FXML
    private ImageView bgTile1;

    @FXML
    private ImageView bgTile2;


    private Moto moto;
    private boolean motoOwned;
    private Skin skin;

    @FXML
    private VBox vBoxMoto;

    @FXML
    private Label lblMoto;
    @FXML
    private ProgressBar pbSpeed;

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
    @FXML
    private ImageView motoImage; // ✅ Doit être un ImageView, pas un Image !

    public ShopController(SceneController sceneController, ShopService shopService)
    {
        this.sceneController = sceneController;
        this.shopService = shopService;
    }


    private void animateTileBackground() {
        double tileHeight = 256; // Hauteur d'un tile
        double transitionDuration = 3; // Durée de l'animation en secondes

        // Appliquer un masque pour cacher tout ce qui dépasse du cadre
        Rectangle clip = new Rectangle(256, 256);
        bgTile1.getParent().setClip(clip);

        // Position initiale correcte
        bgTile1.setTranslateY(0);
        bgTile2.setTranslateY(-tileHeight); // La deuxième image commence bien au-dessus

        // Animation fluide de la première image
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(transitionDuration), bgTile1);
        transition1.setFromY(0);
        transition1.setToY(tileHeight);
        transition1.setInterpolator(javafx.animation.Interpolator.LINEAR);
        transition1.setCycleCount(TranslateTransition.INDEFINITE);
        transition1.setAutoReverse(false);

        // Animation fluide de la seconde image (elle suit immédiatement la première)
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(transitionDuration), bgTile2);
        transition2.setFromY(-tileHeight);
        transition2.setToY(0);
        transition2.setInterpolator(javafx.animation.Interpolator.LINEAR);
        transition2.setCycleCount(TranslateTransition.INDEFINITE);
        transition2.setAutoReverse(false);

        // Réajustement dès qu’une image sort du cadre
        transition1.setOnFinished(e -> {
            bgTile1.setTranslateY(-tileHeight);
            transition1.playFromStart();
        });

        transition2.setOnFinished(e -> {
            bgTile2.setTranslateY(-tileHeight);
            transition2.playFromStart();
        });

        // Démarrer les animations
        transition1.play();
        transition2.play();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.initializeMotoList();
        this.updateCoins();
        animateTileBackground();
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
        this.pbSpeed.setProgress(moto.getSpeed() / 2);
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
