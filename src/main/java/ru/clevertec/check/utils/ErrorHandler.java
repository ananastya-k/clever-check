package main.java.ru.clevertec.check.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// Class for handling errors and writing them to a file
public class ErrorHandler {

    private static final String defaultPath = "./result.csv";

    /**
     * Writes an error message to the default error file.
     *
     * @param error the error type
     * @param message the error message
     */
    public static void writeToErrorFile(String error, String message) {
        write(error, message, defaultPath);
    }

    /**
     * Writes an error message to a specified error file.
     *
     * @param error the error type
     * @param message the error message
     * @param saveToFile the file to save the error message to
     */
    public static void writeToErrorFile(String error, String message, String saveToFile) {
        write(error, message, saveToFile);
    }

    private static void write(String error, String message, String pathToFile) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(pathToFile))) {
            writer.println("ERROR");
            writer.println(error);
            System.err.println(message + "\n");
        } catch (IOException ex) {
            System.err.println("Failed to write error to file: " + ex.getMessage());
        }
        System.exit(1);
    }

}
