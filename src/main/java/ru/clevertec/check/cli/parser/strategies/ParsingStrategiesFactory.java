package main.java.ru.clevertec.check.cli.parser.strategies;

import main.java.ru.clevertec.check.exceptions.IntertalServerException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParsingStrategiesFactory {
    public FieldParser getParser(Field field) throws IntertalServerException {


        if (Map.class.isAssignableFrom(field.getType())) {
            return new ParametrizedFieldParser();
        } else if(List.class.isAssignableFrom(field.getType())
                || Set.class.isAssignableFrom(field.getType())
                || field.getType().isArray()) {
            throw new IntertalServerException("Sorry. We can't fill the field with type: " + field.getType());
        } else {
            return new SingleFieldParser();
        }

    }
}
