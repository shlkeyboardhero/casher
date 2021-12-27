package com.company.view;

import com.company.control.Controler;
import com.company.model.Client;

import java.io.Console;
import java.util.Scanner;


public class ConsoleView extends Controler {
    Scanner scanner = new Scanner(System.in);

    public void Welcoming() {
        System.out.println("--------------------Банкомат-------------------------");
        System.out.println("Выберите действие: \n 1. Войти по данным\n 2. Создать учетную запись");
        verification();
    }

    private void incorrectPIN() {
        System.out.println("Уже введено не правильных паролей: " + getCurrentClient().getCounterPIN());
    }

    private void verification() {
        while (true) {
            switch (scanner.nextInt()) {
                case 1:
                    while (true) {
                        System.out.println("\f");
                        System.out.println("--------------------Банкомат-------------------------");
                        System.out.println("Введите номер вашей карты: ");
                        long tempCurrentClient = scanner.nextLong();
                        // try catch clientexist оргнаизация получения клиента по cardnumber (1- клиента нет exeprion 2- клиент забанен еще один exrption)
                        boolean tempBool = getCorrectClient(tempCurrentClient); //is Client Exist
                        if (!tempBool) {
                            System.out.println("Введены некорректные данные. Попробуйте снова или создайте уч. запись");
                            System.out.println("\f");
                            Welcoming();
                        } else {
                            setCurrentClient(tempCurrentClient);
                            if (checkBan()) {
                                System.out.println("Ваша карта заблокирована! Обратитесь в поддержку!");
                                pressEnterToContinue();
                                System.out.println("\f");
                                Welcoming();
                            } else {
                                if (getCurrentClient().getCounterPIN() != 0)
                                    incorrectPIN();
                                System.out.println("Введите пароль: ");
                                while (true) {
                                    int tempPIN = scanner.nextInt();
                                    boolean checkingPIN = checkPIN(tempPIN);
                                    if (checkingPIN == false) {
                                        if ((getCurrentClient().getCounterPIN()) < 3) {
                                            System.out.println("Введен неправильный пароль! Поробуйте снова!");
                                            incorrectPIN();
                                            System.out.println("Введите пароль: ");
                                        } else {
                                            System.out.println("Вы ввели неправильный пароль 3 раза! Ваша карта заблокирована!");
                                            getCurrentClient().setBan(true);
                                            pressEnterToContinue();
                                            Welcoming();
                                            break;
                                        }
                                    } else {
                                        mainMenu();
                                    }
                                }
                            }
                        }
                        break;
                    }
                case 2:
                    System.out.println("\f\f\f\f\f");
                    System.out.println("--------------------Банкомат-------------------------");
                    String firstName;
                    while (true) {
                        System.out.println("Введите ваше имя: ");
                        String tempFirstName = scanner.next();
                        if (checkFIO(tempFirstName) == true) {
                            firstName = tempFirstName;
                            break;
                        }
                    }
                    String secondName;
                    while (true) {
                        System.out.println("Введите вашу Фамилию: ");
                        String tempSecondName = scanner.next();
                        if (checkFIO(tempSecondName) == true) {
                            secondName = tempSecondName;
                            break;
                        }
                    }
                    String patronymic;
                    while (true) {
                        System.out.println("Введите ваше Отчество: ");
                        String tempPatronymic = scanner.next();
                        if (checkFIO(tempPatronymic) == true) {
                            patronymic = tempPatronymic;
                            break;
                        }
                    }
                    int PIN;

                    while (true) {
                        System.out.println("Введите желаемый ПИН-код: ");
                        String tempPIN = scanner.next();
                        if (isNumber(tempPIN) == true) {
                            PIN = Integer.parseInt(tempPIN);
                            break;
                        } else {
                            System.out.println("Вы использовали некоторектный символ или привысили длинну в 4 символа. Введите еще раз!");
                        }
                    }
                    Client Client;
                    Client = createClient(firstName, secondName, patronymic, null);
                    Client.setPIN(PIN);
                    System.out.println("\f");
                    System.out.println("--------------------Банкомат-------------------------");
                    System.out.println("Вы успешно зарегистрированы!\n Номер вашей карты: " + Client.getCardNumber() + "\n Ваш пин-код: " + Client.getPIN());
                    pressEnterToContinue();
                    Welcoming();
                    break;
            }
            break;
        }
    }

    public void operation() {
        while (true) {
            switch (scanner.nextInt()) {
                case 3:
                    System.out.println("\f");
                    long tempOtherCard;
                    while (true) {
                        System.out.println("Введите номер карточки получателя: ");
                        tempOtherCard = scanner.nextLong();
                        boolean tempBool = getCorrectClient(tempOtherCard);
                        if (tempBool == false)
                            System.out.println("Такого клиента не существует! Укажите корректный номер.");
                        else
                            break;
                    }
                    System.out.println("Введите сумму перевода: ");
                    int tempSum = scanner.nextInt();
                    int var = transfer(tempOtherCard, tempSum);
                    if (var == 0) {
                        System.out.println("У вас недостаточно денег для перевода этой суммы!\n Текущий баланс " + getCurrentClient().getCash());
                    } else {
                        System.out.println("Перевод на сумму " + tempSum + "Выполнен");
                    }
                    pressEnterToContinue();
                    break;
                case 1:
                    System.out.println("\f");
                    System.out.println("Введите сумму пополнения счета: ");
                    putCash(scanner.nextInt());
                    System.out.println("Вы успешно пополнили ваш счет! Ваш текущий баланс: " + getCurrentClient().getCash());
                    pressEnterToContinue();
                    break;
                case 2:
                    System.out.println("\f");
                    System.out.println("Введите сумму списания: ");
                    int takeCash = scanner.nextInt();
                    takeCash(takeCash);
                    System.out.println("Вы успешно сняли" + takeCash + " с вашего счета! Ваш текущий баланс: " + getCurrentClient().getCash());
                    pressEnterToContinue();
                    break;
                case 4:
                    System.out.println("\f");
                    System.out.println("Вы уверены что хотите удалить уч. запись?\n 1. Да\n 2. Нет");
                    switch (scanner.nextInt()) {
                        case 1:
                            System.out.println("Введите ваш PIN, для потдверждения: ");
                            while (true) {
                                int tempPIN = scanner.nextInt();
                                boolean checkingPIN = checkPIN(tempPIN);
                                if (checkingPIN == false) {
                                    System.out.println("Введен неправильный PIN! Поробуйте снова!");
                                    incorrectPIN();
                                    System.out.println("Введите PIN: ");
                                } else {
                                    deleteClient();
                                    System.out.println("Вы успешно удалили свою уч.запись");
                                    pressEnterToContinue();
                                    break;
                                }
                            }
                    }
                    break;
            }
            mainMenu();
        }
    }


    public void pressEnterToContinue() {
        System.out.println("Нажмите Enter, чтобы выйти вглавное меню!");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    public void mainMenu() {
        System.out.println("\f");
        System.out.println("--------------------Банкомат-------------------------");
        System.out.println("Добро божаловать, " + getFIO() + "!");
        System.out.println("Выберите действие: \n 1. Внести деньги\n 2. Снять деньги\n 3.Перевести другому клиенту \n 4. Удалить уч. запись");
        operation();
    }

    public boolean checkFIO(String tempFIO) {
        boolean result = isAlpha(tempFIO);
        if (result == false) {
            System.out.println("Вы использовали некоторектный символ. Введите еще раз!");
            return false;
        } else
            return true;
    }

}
