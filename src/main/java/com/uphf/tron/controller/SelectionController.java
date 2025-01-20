package com.uphf.tron.controller;

import com.uphf.tron.service.ShopService;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Controller;

@Controller
public class SelectionController
{
    private final SceneController sceneController;

    @FXML
    private VBox vBoxMoto;

    public SelectionController(SceneController sceneController, ShopService shopService)
    {
        this.sceneController = sceneController;
    }

    public void goToHome()
    {
        this.sceneController.setScene("home");
    }

    public void launchGame(){
        this.sceneController.setScene("game");
    }
}
