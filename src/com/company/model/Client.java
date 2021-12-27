package com.company.model;

public class Client {

    private String firstName;
    private String lastName;
    private String patronymic;
    private Long cardNumber;
    private int PIN;//маленькими
    private int counterPIN = 0;
    private int Cash;
    private String session = null;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    private String passport;
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

    public Client(String firstName, String lastName, String patronymic, Long cardNumber, String passport) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.cardNumber = cardNumber;
        this.passport = passport;
    }

    public Client(){}


    public Client(String id){
        this.id = id;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

}
