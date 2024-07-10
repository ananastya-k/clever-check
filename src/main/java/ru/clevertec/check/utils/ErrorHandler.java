package main.java.ru.clevertec.check.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorHandler {

    private static final String path = "./result.csv";
    public static void writeToErrorFile(String error, String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            writer.println("ERROR");
            writer.println(error);
            System.err.println(message + "\n");
        } catch (IOException ex) {
            System.err.println("Failed to write error to file: " + ex.getMessage());
        }
        System.exit(1);
    }
}
