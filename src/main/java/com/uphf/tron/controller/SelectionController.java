package com.uphf.tron.controller;

import com.uphf.tron.service.ShopService;
import org.springframework.stereotype.Controller;

@Controller
public class SelectionController
{
    private final SceneController sceneController;

    public SelectionController(SceneController sceneController, ShopService shopService)
    {
        this.sceneController = sceneController;
    }

    public void goToHome()
    {
        this.sceneController.setScene("home");
    }

    public void startGame()
    {
        this.sceneController.setScene("game");
    }
}
