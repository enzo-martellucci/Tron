package com.uphf.tron.controller;

import com.uphf.tron.service.SecurityService;
import com.uphf.tron.service.ShopService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController
{
    private final SceneController sceneController;
    private final SecurityService securityService;

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblError;

    public LoginController(SceneController sceneController, SecurityService securityService)
    {
        this.sceneController = sceneController;
        this.securityService = securityService;
    }

    public void login()
    {
        try
        {
            this.securityService.login(this.txtUsername.getText(), this.txtPassword.getText());
            this.sceneController.setScene("home");
            this.txtUsername.clear();
            this.txtPassword.clear();
            this.lblError.setText("");
        }
        catch (IllegalArgumentException e){ this.lblError.setText(e.getMessage()); }
    }

    public void goToRegister()
    {
        this.sceneController.setScene("register");
    }
}
