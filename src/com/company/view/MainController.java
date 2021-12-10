package com.company.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.company.control.Controler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    private Controler gameController;

    @FXML
    private Button takemoney;

    @FXML
    private Button changePIN;

    @FXML
    private Button deleteAcc;

    @FXML
    private Button balance;

    @FXML
    private Button putmoney;

    @FXML
    private Button transfer;

    @FXML
    private Button exit;

    @FXML
    void initialize() {


    }

    public void setGameController(Controler controller) {
        this.gameController = controller;
    }
}
