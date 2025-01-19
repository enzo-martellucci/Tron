package com.uphf.tron.controller;

import com.uphf.tron.entity.Moto;
import com.uphf.tron.service.ShopService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class ShopController implements Initializable
{
    private final SceneController sceneController;
    private final ShopService shopService;

    @FXML
    private VBox vBoxMoto;

    public ShopController(SceneController sceneController, ShopService shopService)
    {
        this.sceneController = sceneController;
        this.shopService = shopService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
//        Map<Moto, Boolean> mapMotoOwned = this.shopService.getAllMotoOwned();

        List<Moto> lstMoto = this.shopService.getAllMoto();
    }

    public void goToHome()
    {
        this.sceneController.setScene("home");
    }
}
