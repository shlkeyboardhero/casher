package com.company.model;

public class Client {

    private String firstName;
    private String secondName;
    private String patronymic;
    private Long cardNumber;
    private int PIN;//маленькими
    private int counterPIN = 0;
    private int Cash;
    private boolean ban = false;// удалить

    public Long getCardNumber() {
        return cardNumber;
    }

    public boolean isBan() {
        return ban;
    }

    public void setBan(boolean ban) {
        this.ban = ban;
    }


    public int getCounterPIN() {
        return counterPIN;
    }

    public void setCounterPIN(int counterPIN) {
        this.counterPIN = counterPIN;
    }


    public int getPIN() {
        return PIN;
    }

    public void setPIN(int PIN) {
        this.PIN = PIN;
    }

    public Client(String firstName, String secondName, String patronymic, Long cardNumber) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.cardNumber = cardNumber;
    }

    public int getCash() {
        return Cash;
    }

    public void setCash(int cash) {
        Cash = cash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

}
