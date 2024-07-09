package main.java.ru.clevertec.check;

import main.java.ru.clevertec.check.controller.AppController;
import main.java.ru.clevertec.check.exceptions.BadRequestException;
import main.java.ru.clevertec.check.exceptions.IntertalServerException;
import main.java.ru.clevertec.check.exceptions.NotEnoughMoneyException;
import main.java.ru.clevertec.check.view.Receipt;
import main.java.ru.clevertec.check.view.ReceiptSaver;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws NotEnoughMoneyException, BadRequestException, IOException {
        System.out.println("Main class executed successfully.");
        AppController controller = new AppController();
        controller.start(new String[]{ "1-10","2-10","10-7","2-10", "discountCard=4414", "balanceDebitCard=90"});

    }
}
