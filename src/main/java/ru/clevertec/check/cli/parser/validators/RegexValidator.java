package main.java.ru.clevertec.check.cli.parser.validators;

import main.java.ru.clevertec.check.exceptions.BadRequestException;

public class RegexValidator implements IValueValidator{

    @Override
    public boolean validate(String s, String regex) {
        return s.matches(regex);
    }
}
