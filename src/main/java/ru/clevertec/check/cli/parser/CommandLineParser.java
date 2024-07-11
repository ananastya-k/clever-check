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
     * Parses all command line arguments and sets values on the target object's fields.
     *
     * @throws BadRequestException      If a required argument is missing or invalid
     * @throws IllegalAccessException   If there is an error accessing the fields of the target object
     * @throws IntertalServerException  If an internal server error occurs
     */
    public void parse() throws BadRequestException, IllegalAccessException, IntertalServerException {
        Field[] fields = target.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Parameter.class)) {
                field.setAccessible(true);
                parseField(field);
            }
        }

        checkRequired();
    }

    /**
     * Parses a field using the appropriate parser.
     *
     * @param field the field to parse
     * @throws BadRequestException if there is a bad request
     * @throws IntertalServerException if there is an internal server error
     */
    private void parseField(Field field) throws BadRequestException, IntertalServerException {

        FieldParser parser = strategiesFactory.getParser(field);
        parser.parse(field, args, target);
    }

    /**
     * Checks if all required fields are set.
     *
     * @throws IllegalAccessException if the field is not accessible
     * @throws BadRequestException if a required field is missing
     */
    private void checkRequired() throws IllegalAccessException, BadRequestException {
        Field[] fields = target.getClass().getDeclaredFields();

        for(Field field:fields){
            if (field.getAnnotation(Parameter.class).required()) {
                field.setAccessible(true);
                isEmpty(field.get(target));
                field.setAccessible(false);
            }
        }

    }
    /**
     * Checks if a required parameter is missing.
     *
     * @param o The object to check
     * @throws BadRequestException If the parameter is missing or invalid
     */
    private void isEmpty(Object o) throws BadRequestException {
        Optional<Object> optional = Optional.ofNullable(o);
        if (optional.isEmpty()
                || (optional.get() instanceof Collection && ((Collection<?>) optional.get()).isEmpty())
                || (optional.get() instanceof Map && ((Map<?, ?>) optional.get()).isEmpty())) {
            throw new BadRequestException("Missing required argument");
        }
    }
}
