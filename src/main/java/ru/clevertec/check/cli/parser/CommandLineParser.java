package main.java.ru.clevertec.check.cli.parser;

import main.java.ru.clevertec.check.cli.parser.strategies.FieldParser;
import main.java.ru.clevertec.check.cli.parser.strategies.ParsingStrategiesFactory;
import main.java.ru.clevertec.check.exceptions.BadRequestException;
import main.java.ru.clevertec.check.exceptions.IntertalServerException;

import java.lang.reflect.Field;
import java.util.*;

/**
 * CommandLineParser parses command line arguments and sets values on the target object's fields.
 * <p>
 * used the Reflection API because I am weak in this topic
 */

public class CommandLineParser {
    private final Object target;
    private final String[] args;
    private final ParsingStrategiesFactory strategiesFactory = new ParsingStrategiesFactory();

    /**
     * Constructor
     *
     * @param args    The command line arguments
     * @param target  The target object to populate
     */
    public CommandLineParser(String[] args, Object target) {
        this.args = args;
        this.target = target;
    }

    /**
     * Checks if a required parameter is missing.
     *
     * @param o The object to check
     * @throws BadRequestException If the parameter is missing or invalid
     */
    private void checkRequired(Object o) throws BadRequestException, IllegalAccessException {
        Optional<Object> optional = Optional.ofNullable(o);
        if (optional.isEmpty()
                || (optional.get() instanceof Collection && ((Collection<?>) optional.get()).isEmpty())
                || (optional.get() instanceof Map && ((Map<?, ?>) optional.get()).isEmpty())) {
            throw new BadRequestException("Missing required argument");
        }
    }

    /**
     * Parses all command line arguments and sets values on the target object's fields.
     *
     * @throws BadRequestException      If a required argument is missing or invalid
     * @throws IllegalAccessException   If there is an error accessing the fields of the target object
     * @throws IntertalServerException  If an internal server error occurs
     */
    public void parseAll() throws BadRequestException, IllegalAccessException, IntertalServerException, InstantiationException {
        Field[] fields = target.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Parameter.class)) {
                field.setAccessible(true);
                parseField(field);
                field.setAccessible(false);
            }
        }
    }

    private void parseField(Field field) throws BadRequestException, IllegalAccessException, IntertalServerException, InstantiationException {

        FieldParser parser = strategiesFactory.getParser(field);
        parser.parse(field, args, target);

        if (field.getAnnotation(Parameter.class).required()) {
            field.setAccessible(true);
            checkRequired(field.get(target));
        }
    }


}
