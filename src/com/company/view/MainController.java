package com.company.view;

import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.ResourceBundle;

import com.company.control.Controler;
import com.company.dto.TransferDTO;
import com.company.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    private Controler gameController;
    private Client client;

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
        transfer.setOnAction( event -> {

            Dialog<ButtonType> dialog = new Dialog<ButtonType>();
            dialog.setTitle("Внимание!");
            dialog.setContentText( transfer(5294147906167745L, 200).toString());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.show();
        });

        exit.setOnAction(event -> {
            RestTemplate restTemplate = new RestTemplate();

            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
            mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
            restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);


            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", client.getSession());

            HttpEntity<String> request = new HttpEntity<String>(headers);

            ResponseEntity<String> result = restTemplate.exchange("http://localhost:8080/logout", HttpMethod.GET, request, String.class);
        });
    }

    public void setGameController(Controler controller, Client client) {
        this.gameController = controller;
        this.client = client;
    }

    public Integer transfer(Long otherCard, Integer sum){
        RestTemplate restTemplate = new RestTemplate();

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", client.getSession());

        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setSum(sum);
        transferDTO.setOtherCardNumber(otherCard);

        HttpEntity<TransferDTO> request = new HttpEntity<TransferDTO>(transferDTO, headers);

        Integer result = restTemplate.postForObject("http://localhost:8080/card/transfer", request, Integer.class);

        return result;
    }
}
