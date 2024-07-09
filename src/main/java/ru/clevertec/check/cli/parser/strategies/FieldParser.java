package main.java.ru.clevertec.check.cli.parser.strategies;

import com.sun.jdi.InternalException;
import main.java.ru.clevertec.check.cli.parser.Parameter;
import main.java.ru.clevertec.check.exceptions.BadRequestException;
import main.java.ru.clevertec.check.exceptions.IntertalServerException;

import java.lang.reflect.Field;

public interface FieldParser {

    /**
     * Parses a parameterized field and sets its value from the command line arguments.
     *
     * @param field  The field to parse
     * @param args   The command line arguments
     * @param target The target object to populate
     * @throws BadRequestException   If a required argument is missing or invalid
     * @throws InternalException     If an internal server error occurs
     */
    void parse(Field field, String[] args, Object target) throws BadRequestException, IntertalServerException;

    /**
     * Checks if the argument matches the parameter's name or view.
     *
     * @param parameter         The parameter annotation to compare
     * @param arg               The argument (cl) to check
     * @return true if the argument matches the parameter, false otherwise
     */
     default boolean isMatchingArg(Parameter parameter, String arg) {
        return ((!parameter.name().isEmpty() && arg.startsWith(parameter.name()))
                || arg.matches(parameter.view()));
    }

    /**
     * Checks if the argument matches the parameter's name or view.
     *
     * @param parameter         The parameter annotation to check
     * @param value             The parsing value
     * @return true if the argument matches the parameter, false otherwise
     */
     default boolean checkValue(Parameter parameter, String value) {
        return parameter.view().isEmpty() || value.matches(parameter.view());
    }
}
