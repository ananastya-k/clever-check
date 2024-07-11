package main.java.ru.clevertec.check;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class CheckRunner {

    public static void main(String[] args) throws IOException {

        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder processBuilder;
        if (os.contains("win")) {
            generateBAT(args);
            processBuilder = new ProcessBuilder("cmd.exe", "/c", "compile.bat");
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            generateSH(args);
            processBuilder = new ProcessBuilder("bash", "compile.sh");
        } else {
            System.out.println("Can't define OS");
            return;
        }

        processBuilder.inheritIO();
        Process process = processBuilder.start();

        try {
            int exitCode = process.waitFor();
            System.out.println("Script exited with code: " + exitCode);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Script was interrupted");
        }
    }

    private static void generateBAT(String[] args) {
        String path = "./compile.bat";

        try(PrintWriter writer = new PrintWriter(new FileWriter(path))) {

            writer.println("@echo off");
            writer.println("setlocal");
            writer.println("dir /s /B src\\*.java > sources.txt");
            writer.println("javac @sources.txt -d out");
            writer.print("java -cp out main.java.ru.clevertec.check.Main ");
            for (String arg : args) {
                writer.print(arg + " ");
            }
            writer.println();
        } catch (IOException e) {
            Thread.currentThread().interrupt();
            System.err.println("Generation .bat failed with error: " + e.getMessage());
        }
    }

    private static void generateSH(String[] args) throws IOException {

        String path = "./compile.sh";

        try(PrintWriter writer = new PrintWriter(new FileWriter(path))) {

            writer.println("#!/bin/bash");
            writer.println("find src -name \"*.java\" > sources.txt");
            writer.println("javac @sources.txt -d out");
            writer.print("java -cp out main.java.ru.clevertec.check.Main ");
            for (String arg : args) {
                writer.print(arg + " ");
            }
            writer.println();
            writer.close();

            ProcessBuilder processBuilder = new ProcessBuilder("chmod", "+x", path);
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Failed to make script executable, exit code: " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Script was interrupted while making executable");
        } catch (IOException e){
            Thread.currentThread().interrupt();
            System.err.println("Generation .sh failed with error: " + e.getMessage());

        }
    }
}
