package com.uphf.tron.controller;

import com.uphf.tron.service.SecurityService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController
{
    private final SceneController sceneController;
    private final ShopController shopController;
    private final SecurityService securityService;

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblError;

    public LoginController(SceneController sceneController, ShopController shopController, SecurityService securityService)
    {
        this.sceneController = sceneController;
        this.shopController = shopController;
        this.securityService = securityService;
    }

    public void login()
    {
        try
        {
            this.securityService.login(this.txtUsername.getText(), this.txtPassword.getText());
            this.clear();
            this.shopController.initialize(null, null);
            this.sceneController.setScene("home");
        }
        catch (IllegalArgumentException e){ this.lblError.setText(e.getMessage()); }
    }

    private void clear()
    {
        this.txtUsername.clear();
        this.txtPassword.clear();
        this.lblError.setText("");
    }

    public void goToRegister()
    {
        this.clear();
        this.sceneController.setScene("register");
    }
}
