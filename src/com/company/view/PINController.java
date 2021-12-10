package com.company.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.company.control.Controler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PINController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button next;
    private Controler gameController;

    @FXML
    private PasswordField passworld;

    @FXML
    void initialize() {
        next.setOnAction(event -> {
            createDialogWindow();
            try {
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                gameController.createWelcomingView(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        passworld.setTextFormatter(new TextFormatter<String>(filter));

    }

    UnaryOperator<TextFormatter.Change> filter = change -> {
        String s = change.getControlNewText();
        refreshNextButton(s);
        if (s.matches("\\d{0,4}")) {
            return change;
        }
        return null;
    };


    private void refreshNextButton(String s) {
        if (s.length() > 3)
            next.setDisable(false);
        else
            next.setDisable(true);
    }

    private void createDialogWindow(){
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Успешно!");
        dialog.setContentText("Номер вашей карты: \n" + "Ваш ПИН-код: ");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.show();
    }

    public void setGameController(Controler controller) {
        this.gameController = controller;
    }
}
