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

public class CardController {

    @FXML
    private ResourceBundle resources;
    private Controler gameController;

    @FXML
    private URL location;

    @FXML
    private Button back;

    @FXML
    private TextField cardNumber;

    @FXML
    private Button next;

    @FXML
    void initialize() {
        next.setDisable(true);
        next.setOnAction(event -> {

            try {
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                gameController.createMainView(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //createDialogWindow();

            cardNumber.getText();
        });
        back.setOnAction(event -> {
            try {
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                gameController.createWelcomingView(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        cardNumber.setTextFormatter(tf);
    }

    UnaryOperator<TextFormatter.Change> filter = change -> {
        String s = change.getControlNewText();
        refreshNextButton(s);
        if (s.matches("52?\\d{0,14}")) {
            return change;
        }
        return null;
    };

    private void refreshNextButton(String s) {
        if (s.length() > 15)
            next.setDisable(false);
        else
            next.setDisable(true);
    }

    private void createDialogWindow(){
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Внимание!");
        dialog.setContentText("Пользователя с таким номером карты нет в системе! Проверьте введеные данные.");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.show();
    }

    TextFormatter<String> tf = new TextFormatter<String>(TextFormatter.IDENTITY_STRING_CONVERTER, "52",filter);

    public void setGameController(Controler controller) {
        this.gameController = controller;
    }
}
