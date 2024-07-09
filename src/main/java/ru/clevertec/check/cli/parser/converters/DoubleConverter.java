package main.java.ru.clevertec.check.cli.parser.converters;

import main.java.ru.clevertec.check.exceptions.BadRequestException;

import java.util.Optional;
import java.util.OptionalDouble;

public class DoubleConverter implements IStringConverter<Optional<Double>>{

    private final String doubleRegex = "^[-+]?\\d*\\.?\\d+$";
    @Override
    public Optional<Double> convert(String s) {
        return check(s)
                ? Optional.of(Double.parseDouble(s))
                : Optional.empty();
    }

    public boolean check(String s){
        return s.matches(doubleRegex);
    }
}
