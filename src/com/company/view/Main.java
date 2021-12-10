package com.company.view;
import com.company.control.Controler;
import javafx.stage.Stage;
import javafx.application.Application;


public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Controler().createWelcomingView(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
