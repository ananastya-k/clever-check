package main.java.ru.clevertec.check.controller;

import main.java.ru.clevertec.check.cli.parser.*;
import main.java.ru.clevertec.check.exceptions.*;
import main.java.ru.clevertec.check.model.*;
import main.java.ru.clevertec.check.services.impl.*;
import main.java.ru.clevertec.check.utils.ErrorHandler;
import main.java.ru.clevertec.check.view.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static main.java.ru.clevertec.check.utils.ErrorHandler.writeToErrorFile;

public class AppController {


    private ProductService productService;
    private DiscountCardService discountCardService;
    private final String PATH_TO_PRODUCTS_FILE = "./src/main/resources/products.csv";
    private final String PATH_TO_CARDS_FILE = "./src/main/resources/discountCards.csv";

    Parameters parameters;

    class Parameters {
        @Parameter(name = "discountCard=", view = "\\d{4}", required = false)
        Integer discountCardNumber = 0;

        @Parameter(name = "balanceDebitCard=")
        Double balanceDebitCard;

        @Parameter(view = "^\\d{1,8}-\\d{1,8}$")
        Map<Integer, Integer> selectedProducts = new HashMap<>();
    }

    public void start(String[] args) {
        processCommandLineArgs(args);
        processServices();
        processReceipt();

    }

    public void processCommandLineArgs(String[] args) {
        try {
            parameters = new Parameters();
            CommandLineParser pCL = new CommandLineParser(args, parameters);
            pCL.parseAll();
        } catch (BadRequestException e) {
            ErrorHandler.writeToErrorFile("BAD REQUEST", e.getMessage());
        } catch (IntertalServerException | IllegalAccessException e) {
            ErrorHandler.writeToErrorFile("INTERNAL SERVER ERROR", e.getMessage());
        }

    }

    private void processServices() {
        try {
            productService = new ProductService(PATH_TO_PRODUCTS_FILE);
            productService.load();

            discountCardService = new DiscountCardService(PATH_TO_CARDS_FILE);
            discountCardService.load();
        } catch (IOException e) {
            ErrorHandler.writeToErrorFile("INTERNAL SERVER ERROR", e.getMessage());
        }

    }

    private void processReceipt() {
        try {
            OrderController controller = new OrderController(parameters);
            Optional<DiscountCard> discountCardO = discountCardService.getDiscountByNumber(parameters.discountCardNumber);
            Order order;
            Receipt receipt;

            if (discountCardO.isPresent()) {
                order = controller.createOrder(productService, discountCardO.get().getDiscountPercentage());
                receipt = new Receipt(order, discountCardO.get(), parameters.balanceDebitCard);
            } else {
                order = controller.createOrder(productService, 0);
                receipt = new Receipt(order, parameters.balanceDebitCard);
            }

            if (order.getTotalWithDiscount() > parameters.balanceDebitCard) {
                ErrorHandler.writeToErrorFile("NOT ENOUGH MONEY",
                        String.format("Total price with discount: %.2f$. Balance debit card: %.2f$", order.getTotalWithDiscount(), parameters.balanceDebitCard));
            }
            outReceipt(receipt);

        } catch (BadRequestException ex) {
                ErrorHandler.writeToErrorFile("BAD REQUEST", ex.getMessage());
        }

    }

    private void outReceipt(Receipt receipt) {

        try {
            System.out.println(receipt.toString());
            ReceiptSaver printer = new ReceiptSaver();
            printer.generateCheck(receipt, "./result.csv");
        } catch (IOException e) {
            ErrorHandler.writeToErrorFile("INTERNAL SERVER ERROR", e.getMessage());
        }
    }
}
