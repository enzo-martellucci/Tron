package com.uphf.tron.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    private static final int GRID_SIZE = 20; // Taille de la grille
    private static final int CELL_SIZE = 30; // Taille des cellules

    private Rectangle[][] grid = new Rectangle[GRID_SIZE][GRID_SIZE];
    private int playerX = 0; // Position X du joueur
    private int playerY = 0; // Position Y du joueur
    private char direction = 'S'; // Direction initiale du joueur

    @FXML
    private AnchorPane anchorPane; // Lié à l'élément FXML AnchorPane

    @FXML
    public void initialize() {
        System.out.println("Initializing GameController...");
        if (anchorPane == null) {
            throw new IllegalStateException("AnchorPane is not injected properly!");
        }

        initializeGame();

        // Attendre que la scène soit attachée pour configurer les événements
        anchorPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> setDirection(event.getCode().toString()));
            }
        });
    }

    private void initializeGame() {
        System.out.println("Initializing game grid...");

        // Créer un GridPane pour la grille
        GridPane gridPane = new GridPane();

        // Initialiser la grille avec des rectangles
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.GRAY); // Couleur par défaut des cellules
                cell.setStroke(Color.BLACK); // Bordures des cellules
                grid[row][col] = cell;
                gridPane.add(cell, col, row);
            }
        }

        // Marquer la position initiale du joueur
        grid[playerY][playerX].setFill(Color.WHITE);

        // Ajouter le GridPane dans l'AnchorPane
        anchorPane.getChildren().clear(); // Nettoyer les enfants existants
        anchorPane.getChildren().add(gridPane);

        // Ajuster le GridPane dans l'AnchorPane
        AnchorPane.setTopAnchor(gridPane, 0.0);
        AnchorPane.setBottomAnchor(gridPane, 0.0);
        AnchorPane.setLeftAnchor(gridPane, 0.0);
        AnchorPane.setRightAnchor(gridPane, 0.0);

        System.out.println("Game grid initialized and added to AnchorPane.");

        // Configurer la Timeline pour mettre à jour la position du joueur
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.25), event -> movePlayer(direction))
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Répéter indéfiniment
        timeline.play(); // Démarrer la Timeline
    }

    private void setDirection(String key) {
        switch (key) {
            case "W": // Haut (ou Z selon votre clavier)
            case "Z":
                direction = 'Z';
                break;
            case "S": // Bas
                direction = 'S';
                break;
            case "A": // Gauche (ou Q selon votre clavier)
            case "Q":
                direction = 'Q';
                break;
            case "D": // Droite
                direction = 'D';
                break;
            default:
                // Ne rien faire si la touche est invalide
                break;
        }
    }

    private void movePlayer(char direction) {
        // Réinitialiser la position actuelle du joueur (remettre en gris)
        grid[playerY][playerX].setFill(Color.GRAY);

        // Déplacer le joueur en fonction de la direction
        switch (direction) {
            case 'Z': // Haut
                if (playerY > 0) playerY--;
                break;
            case 'S': // Bas
                if (playerY < GRID_SIZE - 1) playerY++;
                break;
            case 'Q': // Gauche
                if (playerX > 0) playerX--;
                break;
            case 'D': // Droite
                if (playerX < GRID_SIZE - 1) playerX++;
                break;
            default:
                // Ne rien faire si la direction est invalide
                break;
        }

        // Mettre à jour la nouvelle position du joueur
        grid[playerY][playerX].setFill(Color.WHITE);
    }
}
