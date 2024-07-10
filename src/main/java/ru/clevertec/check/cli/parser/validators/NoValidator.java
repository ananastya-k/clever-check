package main.java.ru.clevertec.check.cli.parser.validators;

import main.java.ru.clevertec.check.exceptions.BadRequestException;

public class NoValidator implements IValueValidator{

    @Override
    public boolean validate(String arg, String reg) {
        return true;
    }
}
