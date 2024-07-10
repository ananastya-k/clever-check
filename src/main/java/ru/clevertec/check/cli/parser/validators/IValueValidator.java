package main.java.ru.clevertec.check.cli.parser.validators;

import main.java.ru.clevertec.check.exceptions.BadRequestException;

public interface IValueValidator {
    boolean validate(String arg, String reg);
}
