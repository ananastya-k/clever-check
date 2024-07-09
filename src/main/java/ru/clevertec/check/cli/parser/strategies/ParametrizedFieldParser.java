package main.java.ru.clevertec.check.cli.parser.strategies;

import com.sun.jdi.InternalException;
import main.java.ru.clevertec.check.cli.parser.Parameter;
import main.java.ru.clevertec.check.cli.parser.converters.ConvertersFactory;
import main.java.ru.clevertec.check.exceptions.*;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Responsible for parsing fields that are parameterized. It processes
 * command line arguments and populates the corresponding map in the
 * target object with key-value pairs.
 */
public class ParametrizedFieldParser implements FieldParser {
    private final ConvertersFactory factory = new ConvertersFactory();
    Map<Object, Object> map = new HashMap<>();
    Parameter parameter;

    @Override
    public void parse(Field field, String[] args, Object target) throws BadRequestException, InternalException {

        try {

            this.parameter = field.getAnnotation(Parameter.class);

            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
            Class<?> keyType = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            Class<?> valueType = (Class<?>) parameterizedType.getActualTypeArguments()[1];

            Arrays.stream(args)
                    .filter(arg -> isMatchingArg(parameter, arg))
                    .filter(arg -> checkValue(parameter, arg))
                    .forEach(arg -> {
                        try {
                            setToMap( arg, keyType, valueType);
                        } catch (IntertalServerException e) {
                            throw new RuntimeException(e);
                        }
                    });

            field.set(target, map);

        } catch (IllegalAccessException e) {
            throw new RuntimeException("Field access error: ", e);
        }
    }

    private void setToMap( String arg, Class<?> keyType, Class<?> valueType) throws IntertalServerException {

        String value = arg.replace(parameter.name(), "");
        String[] splitArgs = value.split("[-:=]");

        Optional<?> k = (Optional<?>) factory.createConverter(keyType.getSimpleName()).convert(splitArgs[0]);
        Optional<?> v = (Optional<?>) factory.createConverter(valueType.getSimpleName()).convert(splitArgs[1]);

        Object key = k.get();
        Object val = v.get();
        Object currVal = map.getOrDefault(key, 0);

        if (currVal instanceof Number && val instanceof Number) {
            val = (Integer) val + (Integer) currVal;
        }
        map.put(key, val);
    }


}
