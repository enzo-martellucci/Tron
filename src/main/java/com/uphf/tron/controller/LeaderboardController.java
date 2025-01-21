package com.uphf.tron.controller;

import org.springframework.stereotype.Controller;

@Controller
public class LeaderboardController
{
    private SceneController sceneController;

    public LeaderboardController(SceneController sceneController)
    {
        this.sceneController = sceneController;
    }

    public void goToHome()
    {
        this.sceneController.setScene("home");
    }
}
