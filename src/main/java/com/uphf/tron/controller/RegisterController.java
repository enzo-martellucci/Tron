package com.uphf.tron.controller;

import com.uphf.tron.service.SecurityService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class RegisterController
{
    private final SceneController sceneController;
    private final SecurityService securityService;

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtPasswordConfirmation;
    @FXML
    private Label lblError;

    public RegisterController(SceneController sceneController, SecurityService securityService)
    {
        this.sceneController = sceneController;
        this.securityService = securityService;
    }

    public void register()
    {
        try
        {
            this.securityService.register(this.txtUsername.getText(), this.txtPassword.getText(), this.txtPasswordConfirmation.getText());
            this.sceneController.setScene("home");
            this.txtUsername.clear();
            this.txtPassword.clear();
            this.txtPasswordConfirmation.clear();
            this.lblError.setText("");
        }
        catch (IllegalArgumentException e) { this.lblError.setText(e.getMessage()); }
    }

    public void goToLogin()
    {
        this.sceneController.setScene("login");
    }
}
