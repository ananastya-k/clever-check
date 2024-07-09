package main.java.ru.clevertec.check;


import java.io.IOException;

public class CheckRunner {
    public static void main(String[] args)  {

        try {
            // Путь к вашему shell-скрипту
            String scriptPath = "bin/compile.sh";

            // Создаем процесс для выполнения shell-скрипта
            ProcessBuilder pb = new ProcessBuilder("bash", scriptPath);

            // Устанавливаем рабочую директорию, если это необходимо
            // pb.directory(new File("/path/to/your/directory"));

            // Устанавливаем рабочую директорию
            pb.directory(new java.io.File("."));
            // Запускаем процесс
            Process process = pb.start();

            // Ждем завершения процесса
            int exitCode = process.waitFor();

            // Выводим сообщение о завершении процесса
            if (exitCode == 0) {
                System.out.println("Shell script executed successfully");
            } else {
                System.out.println("Shell script execution failed with error code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }
/*
    AppController controller = new AppController();
    ProcessBuilder processBuilder = new ProcessBuilder("javac", "src/**.java");
        processBuilder.inheritIO();

*/
}
