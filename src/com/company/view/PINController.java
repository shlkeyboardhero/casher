package com.company.view;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import com.company.control.Controler;
import com.company.dto.ClientCardDTO;
import com.company.dto.TransferDTO;
import com.company.model.Client;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class PINController {

    @FXML
    private ResourceBundle resources;



    @FXML
    private Button next;
    private Controler gameController;
    private Client client;
    private Long cardNumber;

    @FXML
    private PasswordField passworld;

    @FXML
    void initialize() {
        next.setOnAction(event -> {
            Client client = login(cardNumber, Integer.parseInt(passworld.getText()));
            try {
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                gameController.createMainView(primaryStage, client);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //extracted(event);
        });

        passworld.setTextFormatter(new TextFormatter<String>(filter));
    }

    private void extracted(javafx.event.ActionEvent event) {
        client.setPIN(Integer.parseInt(passworld.getText()));
        createDialogWindow(createClient(createDto()));
        System.out.println(client.getCardNumber());
        try {
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            gameController.createWelcomingView(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //extracted(event);


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

    private void createDialogWindow(String massage){
        Dialog<ButtonType> dialog = new Dialog<ButtonType>();
        dialog.setTitle("Успешно!");
        dialog.setContentText(massage);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.show();
    }

    //"Номер вашей карты: " + client.getCardNumber() +'\n' + "Ваш ПИН-код: " + client.getPIN()



    public String createClient(ClientCardDTO clientCardDTO){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        //restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        /*JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("firstName", clientCardDTO.getFirstName());
        personJsonObject.put("lastName", clientCardDTO.getLastName());
        personJsonObject.put("patronymic", clientCardDTO.getPatronymic());
        personJsonObject.put("passport", clientCardDTO.getPassport());
        personJsonObject.put("pin", clientCardDTO.getPIN());
        personJsonObject.put("cardNumber", clientCardDTO.getCardNumber());*/

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        HttpEntity<ClientCardDTO> request = new HttpEntity<ClientCardDTO>(clientCardDTO, headers);
        //HttpEntity<String> request = new HttpEntity<>(personJsonObject.toString(), headers);
        String output = restTemplate.postForObject("http://localhost:8080/reg", request, String.class);
        return output;
    }

    public ClientCardDTO createDto(){
        ClientCardDTO clientCardDTO = new ClientCardDTO();
        clientCardDTO.setFirstName(client.getFirstName());
        clientCardDTO.setLastName(client.getLastName());
        clientCardDTO.setPatronymic(client.getPatronymic());
        clientCardDTO.setPassport(client.getPassport());
        clientCardDTO.setPIN(client.getPIN());
        clientCardDTO.setCardNumber(client.getCardNumber());
        return  clientCardDTO;
    }

    public Client login(Long cardNumber, int pin){
        RestTemplate restTemplate = new RestTemplate();

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        String plainCreds = cardNumber + ":" + pin;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes =  Base64.getEncoder().encode(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<String> request = new HttpEntity<String>(headers);
        ResponseEntity<Client> response = restTemplate.exchange("http://localhost:8080/card/login", HttpMethod.GET, request, Client.class);
        Client client = response.getBody();
        String set_cookie = response.getHeaders().getFirst(headers.SET_COOKIE);
        client.setSession(set_cookie);
        return client;
    }

    public void setGameController(Controler controller, Client client) {
        this.gameController = controller;
        this.client = client;
    }

    public void setGameController(Controler controller, Long cardNumber) {
        this.gameController = controller;
        this.cardNumber = cardNumber;
    }
}
