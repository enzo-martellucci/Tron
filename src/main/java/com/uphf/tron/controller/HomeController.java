package com.uphf.tron.controller;

import com.uphf.tron.service.SecurityService;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController
{
    private final SceneController sceneController;
    private final SelectionController selectionController;
    private final ShopController shopController;
    private final SecurityService securityService;

    public HomeController(SceneController sceneController, SelectionController selectionController, ShopController shopController, SecurityService securityService)
    {
        this.sceneController = sceneController;
        this.selectionController = selectionController;
        this.shopController = shopController;
        this.securityService = securityService;
    }

    public void goToPlay()
    {
        this.sceneController.setScene("selection");
        this.selectionController.initialize(null, null);
    }

    public void goToShop()
    {
        this.sceneController.setScene("shop");
        this.shopController.initialize(null, null);
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
