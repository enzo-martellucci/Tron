package com.uphf.tron.controller;

import com.uphf.tron.service.SecurityService;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController
{
    private final SceneController sceneController;
    private final SecurityService securityService;

    public HomeController(SceneController sceneController, SecurityService securityService)
    {
        this.sceneController = sceneController;
        this.securityService = securityService;
    }

    public void goToPlay()
    {
        this.sceneController.setScene("selection");
    }

    public void goToShop()
    {
        this.sceneController.setScene("shop");
    }

    public void goToLeaderboard()
    {
        this.sceneController.setScene("leaderboard");
    }

    public void logout()
    {
        this.securityService.logout();
        this.sceneController.setScene("login");
    }
}
