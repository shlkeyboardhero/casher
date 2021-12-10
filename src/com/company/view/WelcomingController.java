package com.company.view;

import com.company.control.Controler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomingController {

    @FXML
    private Button enter;


    @FXML
    private Button register;
    private Controler gameController;

    @FXML
    void initialize() {
        enter.setOnAction(event -> {
            try {
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                gameController.swapCardView(primaryStage);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        register.setOnAction(event -> {
            try {
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                gameController.swapCreateClient(primaryStage);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setGameController(Controler controller) {
        this.gameController = controller;
    }
}


