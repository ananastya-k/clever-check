package main.java.ru.clevertec.check.cli.parser.converters;

import java.util.Optional;

public class NoConverter implements IStringConverter{

    @Override
    public Optional<String> convert(String s) {
        return Optional.of(s);
    }

    @Override
    public boolean check(String s) {
        return true;
    }
}
