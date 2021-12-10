package com.company.control;

import com.company.model.Client;
import com.company.view.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Controler {

    private Pattern pinPattern = Pattern.compile("\\d\\d\\d\\d");

    private Client currentClient = null;

    Map<Long, Client> clientList = new HashMap<Long, Client>();

    public Client getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(Long clientNumber) {
        currentClient = clientList.get(clientNumber);
    }


    public boolean getCorrectClient(Long cardNumber) {
        return clientList.containsKey(cardNumber);
    }

    public boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    public boolean isNumber(String number) {
        return pinPattern.matcher(number).matches();
    }


    public boolean checkBan() {
        return currentClient.isBan();
    }

    public Client createClient(String firstName, String secondName, String patronymic) {
        long cardNumber = randCard();
        Client tempClient = new Client(firstName, secondName, patronymic, cardNumber);
        clientList.put(cardNumber, tempClient);
        return tempClient;
    }

    public String getFIO() {
        return currentClient.getSecondName() + " " + currentClient.getFirstName() + " " + currentClient.getPatronymic();
    }

    public Long randCard() {
        long first14 = (long) (Math.random() * 100000000000000L);
        long number = 5200000000000000L + first14;
        return number;
    }

    public void createPIN(Client currentClient, int userPIN) {
        currentClient.setPIN(userPIN);
    }

    public int transfer(Long otherClientCardNumber, int cashMount) {
        Client otherClient = clientList.get(otherClientCardNumber);
        int currentClientCash = currentClient.getCash();
        int cashDif = currentClientCash - cashMount;
        if (cashDif < 0)
            return 0;
        else
            otherClient.setCash(otherClient.getCash() + cashMount);
        currentClient.setCash(cashDif);
        return 1;
    }

    public void putCash(int cashMount) {
        currentClient.setCash(currentClient.getCash() + cashMount);
    }

    public void takeCash(int cashMount) {
        currentClient.setCash(currentClient.getCash() - cashMount);
    }

    public boolean checkPIN(int PIN) {
        if (currentClient.getPIN() != PIN) {
            currentClient.setCounterPIN(currentClient.getCounterPIN() + 1);
            return false;
        } else {
            currentClient.setCounterPIN(0);
            return true;
        }
    }

    public void getClientFromDB(String cardNumber){

    }


    public void getBaned() {
        currentClient.setBan(true);
    }

    public void deleteClient() {
        clientList.remove(currentClient.getCardNumber());
    }

    public void createWelcomingView(Stage primaryStage) throws IOException {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/company/view/welcoming.fxml"));
            root = loader.load();
            WelcomingController controllerEditBook = loader.getController(); //получаем контроллер для второй формы
            controllerEditBook.setGameController(this);
            primaryStage.setTitle("ATM");
            primaryStage.setScene(new Scene(root, 890, 560));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void swapCardView(Stage primaryStage) throws IOException{
        Scene root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/company/view/card.fxml"));
            root = loader.load();
            CardController controllerEditBook = loader.getController(); //получаем контроллер для второй формы
            controllerEditBook.setGameController(this);
            primaryStage.setScene(root);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void swapCreateClient(Stage primaryStage) throws IOException{
        Scene root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/company/view/makeacc.fxml"));
            root = loader.load();
            CreateClientController controllerEditBook = loader.getController(); //получаем контроллер для второй формы
            controllerEditBook.setGameController(this);
            primaryStage.setScene(root);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createMainView(Stage primaryStage) throws IOException{
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/company/view/main.fxml"));
            root = loader.load();
            MainController controllerEditBook = loader.getController(); //получаем контроллер для второй формы
            controllerEditBook.setGameController(this);
            primaryStage.setScene(new Scene(root, 890, 560));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void swapPINView(Stage primaryStage) throws IOException{
        Scene root = null;
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/company/view/makeACCPIN.fxml"));
            root = loader.load();
            PINController controllerEditBook = loader.getController(); //получаем контроллер для второй формы
            controllerEditBook.setGameController(this);
            primaryStage.setScene(root);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
