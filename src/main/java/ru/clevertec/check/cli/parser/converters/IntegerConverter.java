package main.java.ru.clevertec.check.cli.parser.converters;

import java.util.Optional;

public class IntegerConverter implements IStringConverter<Optional<Integer>>{
    private final String intRegex = "^[+-]?\\d+$";

    @Override
    public Optional<Integer> convert(String s) {
        return check(s)
                ? Optional.of(Integer.parseInt(s))
                : Optional.empty();
    }

    public boolean check(String s){
        return s.matches(intRegex);
    }
}
