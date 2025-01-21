package com.uphf.tron.controller;

import com.uphf.tron.service.SecurityService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SceneController
{
    @Autowired
    private SecurityService securityService;

    private static final String INITIAL_SCENE = "login";

    private ApplicationContext applicationContext;

    private Stage stage;
    private Scene scene;

    private Map<String, Parent> mapScene;

    public void init(ApplicationContext applicationContext, Stage stage)
    {
        this.applicationContext = applicationContext;
        this.scene = new Scene(new AnchorPane());
        this.stage = stage;
        this.stage.setScene(this.scene);

        this.mapScene = new HashMap<>();
        this.setScene(INITIAL_SCENE);

        stage.setTitle("Tron");
        stage.show();
    }

    public void setScene(String name)
    {
        if (!this.mapScene.containsKey(name))
        {
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource("/views/%s.fxml".formatted(name)));
            loader.setControllerFactory(this.applicationContext::getBean);
            try
            {
                this.mapScene.put(name, loader.load());
            } catch (Exception e) {e.printStackTrace();}
        }

        this.scene.setRoot(this.mapScene.get(name));
    }
}
