package main.java.ru.clevertec.check.controller;

import main.java.ru.clevertec.check.exceptions.BadRequestException;
import main.java.ru.clevertec.check.model.*;
import main.java.ru.clevertec.check.view.Receipt;
import main.java.ru.clevertec.check.utils.ErrorHandler;
import main.java.ru.clevertec.check.view.ReceiptSaver;

import java.io.IOException;
import java.util.Optional;
/**
 * Processes the receipt by managing order creation, discount application, and printing.
 */
public class ReceiptProcessor {

    private final Parameters parameters;
    private final ServiceInitializer services;

    /**
     * Constructor to initialize parameters and services.
     *
     * @param parameters the parameters for the receipt
     * @param services the services initializer
     */
    public ReceiptProcessor(Parameters parameters, ServiceInitializer services) {
        this.parameters = parameters;
        this.services = services;
    }

    /**
     * Processes the receipt by creating and validating the order, and printing the receipt.
     *
     * @throws BadRequestException if there is a problem with the request
     */
    public void processReceipt() throws BadRequestException {
        OrderController controller = new OrderController(parameters);
        Optional<DiscountCard> discountOptional = services.getDiscountCardService()
                                                        .getDiscountByNumber(parameters.discountCardNumber);

        int discount = discountOptional.map(DiscountCard::getDiscountPercentage).orElse(0);
        Order order =  controller.createOrder(services.getProductService(), discount);
        validateOrder(order);

        Receipt.Builder receiptBuilder = new Receipt.Builder()
                .setOrder(order)
                .setBalanceDebitCard(parameters.balanceDebitCard);
        discountOptional.ifPresent(receiptBuilder::setDiscountCard);

        Receipt receipt = receiptBuilder.build();
        print(receipt);

    }

    /**
     * Processes the receipt by creating and validating the order, and printing the receipt.
     *
     * @throws BadRequestException if there is a problem with the request
     */
    private void validateOrder(Order order) {
        if (order.getTotalWithDiscount() > parameters.balanceDebitCard) {
            ErrorHandler.writeToErrorFile("NOT ENOUGH MONEY",
                    String.format("Total price with discount: %.2f$. Balance debit card: %.2f$",
                            order.getTotalWithDiscount(), parameters.balanceDebitCard));
        }
    }

    /**
     * Prints the receipt to the console and saves it to a file.
     *
     * @param receipt the receipt to print
     */
    private void print(Receipt receipt) {
        try {
            System.out.println(receipt.toString());
            ReceiptSaver printer = new ReceiptSaver();
            printer.generateCheck(receipt, parameters.PATH_TO_SAVE);
        } catch (IOException e) {
            ErrorHandler.writeToErrorFile("INTERNAL SERVER ERROR", e.getMessage(), parameters.PATH_TO_SAVE);
        }
    }
}
