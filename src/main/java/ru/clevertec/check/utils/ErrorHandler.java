package main.java.ru.clevertec.check.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorHandler {

    private static final String defaultPath = "./result.csv";
    public static void writeToErrorFile(String error, String message) {
        write(error, message, defaultPath);
    }
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
