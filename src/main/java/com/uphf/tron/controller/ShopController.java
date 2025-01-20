package com.uphf.tron.controller;

import com.uphf.tron.entity.Moto;
import com.uphf.tron.service.ShopService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import javafx.scene.shape.Rectangle;

import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


@Controller
public class ShopController implements Initializable
{
    private final SceneController sceneController;
    private final ShopService shopService;
    @FXML
    private ImageView bgTile1;

    @FXML
    private ImageView bgTile2;


    @FXML
    private VBox vBoxMoto;

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
//        Map<Moto, Boolean> mapMotoOwned = this.shopService.getAllMotoOwned();

        List<Moto> lstMoto = this.shopService.getAllMoto();
        animateTileBackground();

    }

    public void goToHome()
    {
        this.sceneController.setScene("home");
    }
}
