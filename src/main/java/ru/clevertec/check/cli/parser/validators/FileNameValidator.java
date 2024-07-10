package main.java.ru.clevertec.check.cli.parser.validators;

import main.java.ru.clevertec.check.exceptions.BadRequestException;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class FileNameValidator implements IValueValidator{

    @Override
    public boolean validate(String arg, String reg) {
        try{
            Path.of(arg);
            return true;
        }catch (InvalidPathException | NullPointerException e){
            return false;
        }
    }
}
