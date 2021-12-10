package com.company.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.company.control.Controler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

public class CreateClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    private Controler gameController;

    @FXML
    private Button back;

    @FXML
    private Button next;

    @FXML
    private TextField lastname;

    @FXML
    private TextField otch;

    @FXML
    private TextField firstname;

    @FXML
    void initialize() {
        back.setOnAction(event -> {
            try {
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                gameController.createWelcomingView(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        next.setDisable(true);
        firstname.setTextFormatter(new TextFormatter<String>(filter));
        lastname.setTextFormatter(new TextFormatter<String>(filter));
        otch.setTextFormatter(new TextFormatter<String>(filter));
        otch.setOnKeyTyped(event -> {refreshNextButton();});
        lastname.setOnKeyTyped(event -> {refreshNextButton();});
        firstname.setOnKeyTyped(event -> {refreshNextButton();});
        next.setOnAction(event -> {
            try {
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            gameController.swapPINView(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }});
    }

    UnaryOperator<TextFormatter.Change> filter = change -> {
        String s = change.getControlNewText();
        if (s.matches("^([а-я]*?$)")) {
            return change;
        }
        return null;
    };


    private void refreshNextButton() {
        if (firstname.getText().length() > 3 && lastname.getText().length() > 3 && otch.getText().length() > 3)
            next.setDisable(false);
        else
            next.setDisable(true);
    }

    public void setGameController(Controler controller) {
        this.gameController = controller;
    }
}
