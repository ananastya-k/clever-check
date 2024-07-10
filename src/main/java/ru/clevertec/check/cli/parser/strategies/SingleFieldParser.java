package main.java.ru.clevertec.check.cli.parser.strategies;

import main.java.ru.clevertec.check.cli.parser.Parameter;
import main.java.ru.clevertec.check.cli.parser.converters.*;
import main.java.ru.clevertec.check.exceptions.*;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * Responsible for parsing and setting values for non-parameterized fields,
 * such as primitive types or objects, based on command line arguments.
 */
public class SingleFieldParser implements FieldParser {
    private final ConvertersFactory factory = new ConvertersFactory();
    Parameter parameter;
    @Override
    public void parse(Field field, String[] args, Object target) throws BadRequestException, IntertalServerException, InstantiationException, IllegalAccessException {
        this.parameter = field.getAnnotation(Parameter.class);

        for(String arg : args){
            if(isMatchingArg(parameter, arg)){
                String value = arg.replace(parameter.name(), "");
                if(!checkValue(parameter, value))
                    throw new BadRequestException("Invalid argument value: " + arg);

                setValue(field, value, target);
            }
        }
    }

    private void setValue(Field field, String arg, Object target) throws BadRequestException, IntertalServerException {

        try {

            field.setAccessible(true);
            IStringConverter<?> converter = factory.createConverter(field.getType().getSimpleName());
            Optional<?> optional =  (Optional<?>) converter.convert(arg);
            Object value = optional.orElseThrow(()->new BadRequestException("Incorrect field input: " + arg));
            field.set(target, value);

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } finally {
            field.setAccessible(false);
        }
    }
}

