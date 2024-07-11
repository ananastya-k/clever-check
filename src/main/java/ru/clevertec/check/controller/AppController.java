package main.java.ru.clevertec.check.controller;

import main.java.ru.clevertec.check.exceptions.*;
import main.java.ru.clevertec.check.utils.ErrorHandler;
import java.io.IOException;

/**
 * Main application controller that initializes parameters and services,
 * and processes receipts.
 */
public class AppController {

    private Parameters parameters;

    private ServiceInitializer services;

    /**
     * Starts the application with given arguments.
     *
     * @param args the command line arguments
     */
    public void start(String[] args) {
        try{
            parameters = new Parameters();
            parameters.init(args);

            services = new ServiceInitializer(parameters.pathToFile);
            services.processServices();

            ReceiptProcessor receiptProcessor = new ReceiptProcessor(parameters, services);
            receiptProcessor.processReceipt();

        }  catch (BadRequestException e) {
            handleError("BAD REQUEST", e.getMessage());

        } catch (IntertalServerException | IllegalAccessException | IOException e) {
            handleError("INTERNAL SERVER ERROR", e.getMessage());
        }
    }

    /**
     * Handles errors by writing them to a file if specified.
     *
     * @param errorType the type of error
     * @param message the error message
     */
    private void handleError(String errorType, String message) {
        if ( parameters.saveToFile != null) {
            ErrorHandler.writeToErrorFile(errorType, message,  parameters.saveToFile);
        } else {
            ErrorHandler.writeToErrorFile(errorType, message);
        }
    }


}
